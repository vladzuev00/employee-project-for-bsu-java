package by.bsu.zuevvlad.thirdlab.databaseconnection.file;

import by.bsu.zuevvlad.thirdlab.daemonthreadfactory.DaemonThreadFactory;
import by.bsu.zuevvlad.thirdlab.databaseconnection.DataBaseConnection;
import by.bsu.zuevvlad.thirdlab.databaseconnection.file.exception.*;
import by.bsu.zuevvlad.thirdlab.entity.Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class FileDataBaseConnection<TypeOfEntity extends Entity>
        implements DataBaseConnection<TypeOfEntity>
{
    private final File connectedFileDataBase;
    private final Lock lock;
    private final Condition conditionOfReloading;
    private boolean reloading;

    public FileDataBaseConnection(final File connectedFileDataBase)
            throws FileDataBaseConnectionCreatingException
    {
        super();
        if(!(connectedFileDataBase.exists() && connectedFileDataBase.isFile()))
        {
            throw new FileDataBaseConnectionCreatingException("Impossible to connect to file data base '"
                    + connectedFileDataBase.getAbsolutePath() + "'.");
        }
        this.connectedFileDataBase = connectedFileDataBase;
        this.lock = new ReentrantLock();
        this.conditionOfReloading = this.lock.newCondition();
        this.reloading = false;
    }

    public final File getConnectedFileDataBase()
    {
        return this.connectedFileDataBase;
    }

    @Override
    public final void loadEntity(final TypeOfEntity loadedEntity)
            throws FileDataBaseConnectionLoadingException
    {
        this.lock.lock();
        try(final FileOutputStream fileOutputStream = new FileOutputStream(this.connectedFileDataBase, true);
            final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream))
        {
            while(this.reloading)
            {
                this.conditionOfReloading.await();
            }
            objectOutputStream.writeObject(loadedEntity);
        }
        catch(final IOException | InterruptedException cause)
        {
            throw new FileDataBaseConnectionLoadingException(cause);
        }
        finally
        {
            this.lock.unlock();
        }
    }

    @Override
    public final Future<Collection<TypeOfEntity>> offloadEntities()
    {
        final ExecutorService executorService = Executors.newSingleThreadExecutor(
                FileDataBaseConnection.DAEMON_THREAD_FACTORY);
        return executorService.submit(new OffLoaderFileDataBase());
    }

    private static final DaemonThreadFactory DAEMON_THREAD_FACTORY = new DaemonThreadFactory();

    private final class OffLoaderFileDataBase implements Callable<Collection<TypeOfEntity>>
    {
        public OffLoaderFileDataBase()
        {
            super();
        }

        @Override
        public final Collection<TypeOfEntity> call()
                throws FileDataBaseConnectionOffloadingException
        {
            final Collection<TypeOfEntity> deserializedEntities = new ArrayList<TypeOfEntity>();
            FileDataBaseConnection.this.lock.lock();
            try(final FileInputStream fileInputStream
                        = new FileInputStream(FileDataBaseConnection.this.connectedFileDataBase);
                final BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                final ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream))
            {
                while(FileDataBaseConnection.this.reloading)
                {
                    FileDataBaseConnection.this.conditionOfReloading.await();
                }
                Object lastDeserializedObject;
                TypeOfEntity lastDeserializedEntity;
                while(true)
                {
                    lastDeserializedObject = objectInputStream.readObject();
                    if(!(lastDeserializedObject instanceof Entity))
                    {
                        throw new FileDataBaseConnectionOffloadingException("Object was found in data base, which is not "
                                + "entity. Class of found object: '" + lastDeserializedObject.getClass().getName() + "'.");
                    }
                    lastDeserializedEntity = (TypeOfEntity)lastDeserializedObject;
                    deserializedEntities.add(lastDeserializedEntity);
                }
            }
            catch(final EOFException exceptionOfEndDeserialization)
            {
                return deserializedEntities;
            }
            catch(final IOException | ClassNotFoundException | InterruptedException cause)
            {
                throw new FileDataBaseConnectionOffloadingException(cause);
            }
            finally
            {
                FileDataBaseConnection.this.lock.unlock();
            }
        }
    }

    @Override
    public final Future<Boolean> reloadEntities(final Collection<TypeOfEntity> newEntitiesOfDataBase)
    {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        return executorService.submit(new ReLoaderFileDataBase(newEntitiesOfDataBase));
    }

    private final class ReLoaderFileDataBase implements Callable<Boolean>
    {
        private final Collection<TypeOfEntity> newEntitiesOfDataBase;

        public ReLoaderFileDataBase(final Collection<TypeOfEntity> newEntitiesOfDataBase)
        {
            super();
            this.newEntitiesOfDataBase = newEntitiesOfDataBase;
        }

        @Override
        public final Boolean call()
        {
            FileDataBaseConnection.this.lock.lock();
            FileDataBaseConnection.this.reloading = true;
            boolean successReloading;
            try(final FileOutputStream fileOutputStream
                        = new FileOutputStream(FileDataBaseConnection.this.connectedFileDataBase);
                final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                final ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream))
            {
                for(final TypeOfEntity serializedEntity : this.newEntitiesOfDataBase)
                {
                    objectOutputStream.writeObject(serializedEntity);
                }
                successReloading = true;
            }
            catch(final IOException cause)
            {
                successReloading = false;
            }
            finally
            {
                FileDataBaseConnection.this.reloading = false;
                FileDataBaseConnection.this.conditionOfReloading.signalAll();
                FileDataBaseConnection.this.lock.unlock();
            }
            return successReloading;
        }
    }
}
