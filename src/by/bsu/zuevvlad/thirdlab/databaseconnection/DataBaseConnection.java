package by.bsu.zuevvlad.thirdlab.databaseconnection;

import by.bsu.zuevvlad.thirdlab.databaseconnection.exception.DataBaseConnectionLoadingException;
import by.bsu.zuevvlad.thirdlab.databaseconnection.exception.DataBaseConnectionReloadingException;
import by.bsu.zuevvlad.thirdlab.entity.Entity;

import java.util.Collection;
import java.util.concurrent.Future;

public interface DataBaseConnection<TypeOfEntity extends Entity>
{
    public abstract void loadEntity(final TypeOfEntity loadedEntity)
            throws DataBaseConnectionLoadingException;
    public abstract Future<Collection<TypeOfEntity>> offloadEntities();
    public abstract Future<Boolean> reloadEntities(final Collection<TypeOfEntity> newEntitiesOfDataBase)
            throws DataBaseConnectionReloadingException;
}
