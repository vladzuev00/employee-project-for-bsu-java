package by.bsu.zuevvlad.thirdlab.entityrepository;

import by.bsu.zuevvlad.thirdlab.databaseconnection.DataBaseConnection;
import by.bsu.zuevvlad.thirdlab.databaseconnection.exception.DataBaseConnectionReloadingException;
import by.bsu.zuevvlad.thirdlab.entity.Entity;
import by.bsu.zuevvlad.thirdlab.entityrepository.exception.EntityRepositoryCreatingException;
import by.bsu.zuevvlad.thirdlab.entityrepository.exception.EntityRepositoryModificationException;
import by.bsu.zuevvlad.thirdlab.entityrepository.exception.EntityRepositoryReloadingDataBaseException;
import by.bsu.zuevvlad.thirdlab.entityrepository.exception.EntityRepositorySearchingException;
import by.bsu.zuevvlad.thirdlab.specification.entity.EntitySpecification;
import by.bsu.zuevvlad.thirdlab.validator.entity.ValidatorForPropertiesOfEntity;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public abstract class EntityRepository<TypeOfEntity extends Entity> implements Iterable<TypeOfEntity>
{
    private final Map<Long, TypeOfEntity> entitiesAndTheirIdentificationNumbers;
    private final DataBaseConnection<TypeOfEntity> dataBaseConnection;

    protected EntityRepository(final DataBaseConnection<TypeOfEntity> dataBaseConnection)
            throws EntityRepositoryCreatingException
    {
        this.dataBaseConnection = dataBaseConnection;
        this.entitiesAndTheirIdentificationNumbers = EntityRepository
                .<TypeOfEntity>offloadEntitiesAndTheirIdentificationNumbers(dataBaseConnection);
    }

    private static <TypeOfEntity extends Entity> Map<Long, TypeOfEntity> offloadEntitiesAndTheirIdentificationNumbers(
            final DataBaseConnection<TypeOfEntity> dataBaseConnection)
            throws EntityRepositoryCreatingException
    {
        try
        {
            final Future<Collection<TypeOfEntity>> holderOfOffloadedEntities = dataBaseConnection.offloadEntities();
            final Collection<TypeOfEntity> offloadedEntities = holderOfOffloadedEntities.get();
            final Map<Long, TypeOfEntity> entitiesAndTheirIdentificationNumbers = new HashMap<Long, TypeOfEntity>();
            for(final TypeOfEntity entity : offloadedEntities)
            {
                entitiesAndTheirIdentificationNumbers.put(entity.getId(), entity);
            }
            return entitiesAndTheirIdentificationNumbers;
        }
        catch(final InterruptedException | ExecutionException cause)
        {
            throw new EntityRepositoryCreatingException(cause);
        }
    }

    public final boolean isContain(final long idOfResearchEntity)
    {
        if(!EntityRepository.VALIDATOR_FOR_PROPERTIES_OF_ENTITY.isValidId(idOfResearchEntity))
        {
            throw new EntityRepositorySearchingException("Impossible to check containing entity in repository"
                    + " with not valid id: " + idOfResearchEntity + ".");
        }
        return this.entitiesAndTheirIdentificationNumbers.containsKey(idOfResearchEntity);
    }

    private static final ValidatorForPropertiesOfEntity VALIDATOR_FOR_PROPERTIES_OF_ENTITY
            = new ValidatorForPropertiesOfEntity();

    public final boolean isContain(final TypeOfEntity researchEntity)
    {
        final long idOfResearchEntity = researchEntity.getId();
        return this.entitiesAndTheirIdentificationNumbers.containsKey(idOfResearchEntity);
    }

    public final void addEntity(final TypeOfEntity addedEntity)
    {
        if(this.isContain(addedEntity))
        {
            throw new EntityRepositoryModificationException("Impossible to add given entity, because it has already "
                    + " been added. Given entity: " + addedEntity + ".");
        }
        this.entitiesAndTheirIdentificationNumbers.put(addedEntity.getId(), addedEntity);
    }

    public final TypeOfEntity findEntity(final long idOfResearchEntity)
    {
        if(!EntityRepository.VALIDATOR_FOR_PROPERTIES_OF_ENTITY.isValidId(idOfResearchEntity))
        {
            throw new EntityRepositorySearchingException("Impossible to find entity with given not valid id: "
                    + idOfResearchEntity + ".");
        }
        if(!this.isContain(idOfResearchEntity))
        {
            throw new EntityRepositorySearchingException("Impossible to find entity with given id, because it has not "
                    + " been added in repository. Given id: " + idOfResearchEntity + ".");
        }
        return this.entitiesAndTheirIdentificationNumbers.get(idOfResearchEntity);
    }

    public final void removeEntity(final long idOfRemovedEntity)
    {
        if(!EntityRepository.VALIDATOR_FOR_PROPERTIES_OF_ENTITY.isValidId(idOfRemovedEntity))
        {
            throw new EntityRepositoryModificationException("Removing entity with given not valid id is impossible. "
                    + "Given id: " + idOfRemovedEntity + ".");
        }
        if(!this.isContain(idOfRemovedEntity))
        {
            throw new EntityRepositoryModificationException("Removing entity with given id is impossible, because "
                    + "repository doesn't have entity with given id. Given id: " + idOfRemovedEntity + ".");
        }
        this.entitiesAndTheirIdentificationNumbers.remove(idOfRemovedEntity);
    }

    public final void removeEntity(final TypeOfEntity removedEntity)
    {
        if(!this.isContain(removedEntity))
        {
            throw new EntityRepositoryModificationException("Removing given entity is impossible, because "
                    + "repository doesn't have it. Given entity: " + removedEntity + ".");
        }
        final long idOfRemovedEntity = removedEntity.getId();
        this.entitiesAndTheirIdentificationNumbers.remove(idOfRemovedEntity);
    }

    public final List<TypeOfEntity> findMatchingEntities(final EntitySpecification<TypeOfEntity> specification)
    {
        final List<TypeOfEntity> matchingEntities = new ArrayList<TypeOfEntity>();
        for(final TypeOfEntity researchEntity : this.entitiesAndTheirIdentificationNumbers.values())
        {
            if(specification.isMatch(researchEntity))
            {
                matchingEntities.add(researchEntity);
            }
        }
        return matchingEntities;
    }

    public final void reloadDataBase()
            throws EntityRepositoryReloadingDataBaseException
    {
        try
        {
            this.dataBaseConnection.reloadEntities(this.entitiesAndTheirIdentificationNumbers.values());
        }
        catch(final DataBaseConnectionReloadingException cause)
        {
            throw new EntityRepositoryReloadingDataBaseException(cause);
        }
    }

    public final int findAmountOfEntities()
    {
        return this.entitiesAndTheirIdentificationNumbers.values().size();
    }

    @Override
    public final Iterator<TypeOfEntity> iterator()
    {
        return new EntityRepositoryIterator();
    }

    private final class EntityRepositoryIterator implements Iterator<TypeOfEntity>
    {
        private final Iterator<TypeOfEntity> valuesIterator;

        public EntityRepositoryIterator()
        {
            this.valuesIterator = EntityRepository.this.entitiesAndTheirIdentificationNumbers.values().iterator();
        }

        @Override
        public final boolean hasNext()
        {
            return this.valuesIterator.hasNext();
        }

        @Override
        public final TypeOfEntity next()
        {
            return this.valuesIterator.next();
        }
    }
}

