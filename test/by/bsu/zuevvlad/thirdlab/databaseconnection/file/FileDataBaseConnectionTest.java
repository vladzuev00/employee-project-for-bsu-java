package by.bsu.zuevvlad.thirdlab.databaseconnection.file;

import by.bsu.zuevvlad.thirdlab.databaseconnection.file.exception.FileDataBaseConnectionCreatingException;
import by.bsu.zuevvlad.thirdlab.databaseconnection.file.exception.FileDataBaseConnectionLoadingException;
import by.bsu.zuevvlad.thirdlab.entity.Entity;
import by.bsu.zuevvlad.thirdlab.idgenerator.IdGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public final class FileDataBaseConnectionTest
{
    public FileDataBaseConnectionTest()
    {
        super();
    }

    @BeforeClass
    public final void createFileDataBaseConnectionAndLoadEntitiesInFileDataBase()
            throws FileDataBaseConnectionCreatingException, IOException
    {
        FileDataBaseConnectionTest.fileDataBaseConnection = new FileDataBaseConnection<SubEntity>(
                FileDataBaseConnectionTest.FILE_DATA_BASE);
        this.loadEntitiesInFileDataBase();
    }

    private static FileDataBaseConnection<SubEntity> fileDataBaseConnection = null;

    private static final class SubEntity extends Entity
    {
        public SubEntity()
        {
            super();
        }

        public SubEntity(final long id)
        {
            super(id);
        }
    }

    private final void loadEntitiesInFileDataBase()
            throws IOException
    {
        try(final FileOutputStream fileOutputStream = new FileOutputStream(FileDataBaseConnectionTest.FILE_DATA_BASE);
            final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream))
        {
            for(final SubEntity serializedEntity
                    : FileDataBaseConnectionTest.entitiesLoadedInFileDataBase)
            {
                objectOutputStream.writeObject(serializedEntity);
            }
        }
    }

    private static final IdGenerator ID_GENERATOR = new IdGenerator();

    private static List<SubEntity> entitiesLoadedInFileDataBase = new ArrayList<SubEntity>()
    {
        {
            this.add(new SubEntity(FileDataBaseConnectionTest.ID_GENERATOR.generateId()));
            this.add(new SubEntity(FileDataBaseConnectionTest.ID_GENERATOR.generateId()));
            this.add(new SubEntity(FileDataBaseConnectionTest.ID_GENERATOR.generateId()));
            this.add(new SubEntity(FileDataBaseConnectionTest.ID_GENERATOR.generateId()));
            this.add(new SubEntity(FileDataBaseConnectionTest.ID_GENERATOR.generateId()));
        }
    };

    private static final String PATH_FROM_CONTENT_ROOT_OF_FILE_DATA_BASE = "data\\fortest\\database";
    private static final File FILE_DATA_BASE = new File(
            FileDataBaseConnectionTest.PATH_FROM_CONTENT_ROOT_OF_FILE_DATA_BASE);

    @Test
    public final void fileDataBaseConnectionShouldBeCreated()
    {
        try
        {
            final FileDataBaseConnection<SubEntity> fileDataBaseConnection
                    = new FileDataBaseConnection<SubEntity>(FileDataBaseConnectionTest.FILE_DATA_BASE);
            final File expectedConnectedFileDataBase = FileDataBaseConnectionTest.FILE_DATA_BASE;
            final File actualConnectedFileDataBase = fileDataBaseConnection.getConnectedFileDataBase();
            Assert.assertSame(actualConnectedFileDataBase, expectedConnectedFileDataBase);
        }
        catch(final FileDataBaseConnectionCreatingException notExpectedException)
        {
            Assert.fail();
        }
    }

    @Test
    public final void fileDataBaseConnectionShouldNotBeCreated()
    {
        boolean exceptionIsArisen = false;
        try
        {
            final FileDataBaseConnection<SubEntity> fileDataBaseConnection
                    = new FileDataBaseConnection<SubEntity>(FileDataBaseConnectionTest.NOT_EXISTING_FILE_DATA_BASE);
        }
        catch(final FileDataBaseConnectionCreatingException expectedException)
        {
            exceptionIsArisen = true;
        }
        Assert.assertTrue(exceptionIsArisen);
    }

    private static final String PATH_FROM_CONTENT_ROOT_OF_NOT_EXISTING_FILE_DATA_BASE = "not existing";
    private static final File NOT_EXISTING_FILE_DATA_BASE = new File(
            FileDataBaseConnectionTest.PATH_FROM_CONTENT_ROOT_OF_NOT_EXISTING_FILE_DATA_BASE);

    @Test
    public final void connectedFileDataBase()
    {
        final File expectedConnectedFileDataBase = FileDataBaseConnectionTest.FILE_DATA_BASE;
        final File actualConnectedFileDataBase
                = FileDataBaseConnectionTest.fileDataBaseConnection.getConnectedFileDataBase();
        Assert.assertSame(actualConnectedFileDataBase, expectedConnectedFileDataBase);
    }

    @Test
    public final void entityShouldBeLoadedInFileDataBase()
    {
        try
        {
            final SubEntity loadedEntity = new SubEntity(FileDataBaseConnectionTest.ID_GENERATOR.generateId());
            final long lengthOfFileBeforeLoadingEntity = FileDataBaseConnectionTest.FILE_DATA_BASE.length();
            FileDataBaseConnectionTest.fileDataBaseConnection.loadEntity(loadedEntity);
            FileDataBaseConnectionTest.entitiesLoadedInFileDataBase.add(loadedEntity);
            final long lengthOfFileAfterLoadingEntity = FileDataBaseConnectionTest.FILE_DATA_BASE.length();
            Assert.assertTrue(lengthOfFileAfterLoadingEntity > lengthOfFileBeforeLoadingEntity);
        }
        catch(final FileDataBaseConnectionLoadingException notExpectedException)
        {
            Assert.fail();
        }
    }

    @Test
    public final void entitiesShouldBeOffloadedFromDataBase()
            throws InterruptedException
    {
        try
        {
            final Future<Collection<SubEntity>> holderOfActualOffloadedEntities
                    = FileDataBaseConnectionTest.fileDataBaseConnection.offloadEntities();
            final Collection<SubEntity> expectedOffloadedEntities
                    = FileDataBaseConnectionTest.entitiesLoadedInFileDataBase;
            Assert.assertEquals(holderOfActualOffloadedEntities.get(), expectedOffloadedEntities);
        }
        catch(final ExecutionException notExpectedException)
        {
            Assert.fail();
        }
    }

    @Test
    public final void entitiesShouldBeReloadedInFileDataBase()
            throws ExecutionException, InterruptedException
    {
        final List<SubEntity> newEntitiesOfFileDataBase = new ArrayList<SubEntity>()
        {
            {
                new SubEntity(FileDataBaseConnectionTest.ID_GENERATOR.generateId());
                new SubEntity(FileDataBaseConnectionTest.ID_GENERATOR.generateId());
            }
        };
        final Future<Boolean> holderOfMarkerOfSuccessOfReloading
                = FileDataBaseConnectionTest.fileDataBaseConnection.reloadEntities(newEntitiesOfFileDataBase);
        final boolean reloadingIsSuccess = holderOfMarkerOfSuccessOfReloading.get();
        final Collection<SubEntity> expectedEntitiesInFileDataBase = newEntitiesOfFileDataBase;
        final Collection<SubEntity> actualEntitiesIfFileDataBase
                = FileDataBaseConnectionTest.fileDataBaseConnection.offloadEntities().get();
        Assert.assertTrue(reloadingIsSuccess
                && actualEntitiesIfFileDataBase.equals(expectedEntitiesInFileDataBase));
        FileDataBaseConnectionTest.entitiesLoadedInFileDataBase = newEntitiesOfFileDataBase;
    }
}
