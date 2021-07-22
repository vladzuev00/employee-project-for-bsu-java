package by.bsu.zuevvlad.thirdlab.entityrepository;

import by.bsu.zuevvlad.thirdlab.databaseconnection.DataBaseConnection;
import by.bsu.zuevvlad.thirdlab.entity.Entity;
import by.bsu.zuevvlad.thirdlab.entityrepository.exception.EntityRepositoryCreatingException;
import by.bsu.zuevvlad.thirdlab.entityrepository.exception.EntityRepositoryModificationException;
import by.bsu.zuevvlad.thirdlab.entityrepository.exception.EntityRepositorySearchingException;
import by.bsu.zuevvlad.thirdlab.idgenerator.IdGenerator;
import by.bsu.zuevvlad.thirdlab.specification.entity.EntitySpecification;
import by.bsu.zuevvlad.thirdlab.validator.entity.ValidatorForPropertiesOfEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class EntityRepositoryTest
{
    @Test
    public final void entityRepositoryShouldBeCreated()
    {
        boolean exceptionIsArisen = false;
        try
        {
            final IdGenerator idGenerator = new IdGenerator();
            final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                    = new ArrayListDataBaseConnection<SubEntity>();
            arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
            arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
            arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
            new SubEntityRepository(arrayListDataBaseConnection);
        }
        catch(final EntityRepositoryCreatingException notExpectedException)
        {
            exceptionIsArisen = true;
        }
        Assert.assertFalse(exceptionIsArisen);
    }

    private static final class ArrayListDataBaseConnection<TypeOfEntity extends Entity>
            implements DataBaseConnection<TypeOfEntity>
    {
        private final List<TypeOfEntity> entities;
        private final ExecutorService executorService;

        public ArrayListDataBaseConnection()
        {
            this.entities = new ArrayList<TypeOfEntity>();
            this.executorService = Executors.newSingleThreadExecutor();
        }

        @Override
        public final void loadEntity(final TypeOfEntity loadedEntity)
        {
            this.entities.add(loadedEntity);
        }

        @Override
        public final Future<Collection<TypeOfEntity>> offloadEntities()
        {
            return this.executorService.submit(() ->
            {
                return ArrayListDataBaseConnection.this.entities;
            });
        }

        @Override
        public final Future<Boolean> reloadEntities(final Collection<TypeOfEntity> newEntitiesOfDataBase)
        {
            return this.executorService.submit(()->
            {
                ArrayListDataBaseConnection.this.entities.clear();
                ArrayListDataBaseConnection.this.entities.addAll(newEntitiesOfDataBase);
                return true;
            });
        }

        public final int findAmountOfEntities()
        {
            return this.entities.size();
        }
    }

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

    private static final class SubEntityRepository extends EntityRepository<SubEntity>
    {
        public SubEntityRepository(final DataBaseConnection<SubEntity> dataBaseConnection)
        {
            super(dataBaseConnection);
        }
    }

    @Test
    public final void entityWithGivenIdShouldBeContainedByRepository()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final long idOfResearchEntity = idGenerator.generateId();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idOfResearchEntity));
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        Assert.assertTrue(entityRepository.isContain(idOfResearchEntity));
    }

    @Test
    public final void entityWithGivenIdShouldNotBeContainedByRepository()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final EntityRepository<SubEntity> entityRepository
            = new SubEntityRepository(arrayListDataBaseConnection);
        final long idOfResearchEntity = idGenerator.generateId();
        Assert.assertFalse(entityRepository.isContain(idOfResearchEntity));
    }

    @Test(expectedExceptions = EntityRepositorySearchingException.class)
    public final void checkingContainingEntityByRepositoryByNotValidIdShouldBeImpossible()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        final long idOfResearchEntity = ValidatorForPropertiesOfEntity.MINIMAL_ALLOWABLE_VALUE_OF_ID - 1;
        entityRepository.isContain(idOfResearchEntity);
    }

    @Test
    public final void entityShouldBeContainedByRepository()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final SubEntity researchEntity = new SubEntity(idGenerator.generateId());
        arrayListDataBaseConnection.loadEntity(researchEntity);
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        Assert.assertTrue(entityRepository.isContain(researchEntity));
    }

    @Test
    public final void entityShouldNotBeContainedByRepository()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final SubEntity researchEntity = new SubEntity(idGenerator.generateId());
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        Assert.assertFalse(entityRepository.isContain(researchEntity));
    }

    @Test
    public final void entityShouldBeAddedInRepository()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        final int amountOfEntitiesOfRepositoryBeforeAdding = entityRepository.findAmountOfEntities();
        final SubEntity addedEntity = new SubEntity(idGenerator.generateId());
        try
        {
            entityRepository.addEntity(addedEntity);
        }
        catch(final EntityRepositoryModificationException notExpectedException)
        {
            Assert.fail();
        }
        final int expectedAmountOfEntitiesOfRepository = amountOfEntitiesOfRepositoryBeforeAdding + 1;
        final int actualAmountOfEntitiesOfRepository = entityRepository.findAmountOfEntities();
        Assert.assertEquals(actualAmountOfEntitiesOfRepository, expectedAmountOfEntitiesOfRepository);
    }

    @Test
    public final void entityShouldNotBeAddedInRepository()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final SubEntity addedEntity = new SubEntity(idGenerator.generateId());
        arrayListDataBaseConnection.loadEntity(addedEntity);
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        boolean exceptionIsArisen = false;
        try
        {
            entityRepository.addEntity(addedEntity);
        }
        catch(final EntityRepositoryModificationException expectedException)
        {
            exceptionIsArisen = true;
        }
        Assert.assertTrue(exceptionIsArisen);
    }

    @Test
    public final void entityShouldBeFoundByItsId()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final long idOfResearchEntity = idGenerator.generateId();
        final SubEntity expectedFoundEntity = new SubEntity(idOfResearchEntity);
        arrayListDataBaseConnection.loadEntity(new SubEntity(idOfResearchEntity));
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        final SubEntity actualFoundEntity = entityRepository.findEntity(idOfResearchEntity);
        Assert.assertEquals(actualFoundEntity, expectedFoundEntity);
    }

    @Test
    public final void entityShouldNotBeFoundByItsId()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        final long idOfResearchEntity = idGenerator.generateId();
        boolean exceptionIsArisen = false;
        try
        {
            entityRepository.findEntity(idOfResearchEntity);
        }
        catch(final EntityRepositorySearchingException expectedException)
        {
            exceptionIsArisen = true;
        }
        Assert.assertTrue(exceptionIsArisen);
    }

    @Test
    public final void findingEntityByItsNotValidIdShouldBeImpossible()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        final long idOfResearchEntity = ValidatorForPropertiesOfEntity.MINIMAL_ALLOWABLE_VALUE_OF_ID - 1;
        boolean exceptionIsArisen = false;
        try
        {
            entityRepository.findEntity(idOfResearchEntity);
        }
        catch(final EntityRepositorySearchingException expectedException)
        {
            exceptionIsArisen = true;
        }
        Assert.assertTrue(exceptionIsArisen);
    }

    @Test
    public final void entityShouldBeRemovedByItsId()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final long idOfRemovedEntity = idGenerator.generateId();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idOfRemovedEntity));
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        final int amountOfEntitiesBeforeRemoving = entityRepository.findAmountOfEntities();
        entityRepository.removeEntity(idOfRemovedEntity);
        final int expectedAmountOfEntities = amountOfEntitiesBeforeRemoving - 1;
        final int actualAmountOfEntities = entityRepository.findAmountOfEntities();
        Assert.assertEquals(actualAmountOfEntities, expectedAmountOfEntities);
    }

    @Test(expectedExceptions = EntityRepositoryModificationException.class)
    public final void entityShouldNotBeRemovedByItsId()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        final long idOfRemovedEntity = idGenerator.generateId();
        entityRepository.removeEntity(idOfRemovedEntity);
    }

    @Test(expectedExceptions = EntityRepositoryModificationException.class)
    public final void removingEntityByNotValidIdShouldBeImpossible()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        final long idOfRemovedEntity = ValidatorForPropertiesOfEntity.MINIMAL_ALLOWABLE_VALUE_OF_ID - 1;
        entityRepository.removeEntity(idOfRemovedEntity);
    }

    @Test
    public final void entityShouldBeRemovedFromRepository()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final SubEntity removedEntity = new SubEntity(idGenerator.generateId());
        arrayListDataBaseConnection.loadEntity(removedEntity);
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        final int amountOfEntitiesBeforeRemoving = entityRepository.findAmountOfEntities();
        entityRepository.removeEntity(removedEntity);
        final int expectedAmountOfEntities = amountOfEntitiesBeforeRemoving - 1;
        final int actualAmountOfEntities = entityRepository.findAmountOfEntities();
        Assert.assertEquals(actualAmountOfEntities, expectedAmountOfEntities);
    }

    @Test(expectedExceptions = EntityRepositoryModificationException.class)
    public final void entityShouldNotBeRemovedFromRepository()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        final SubEntity removedEntity = new SubEntity(idGenerator.generateId());
        entityRepository.removeEntity(removedEntity);
    }

    @Test
    public final void entitiesMatchingSpecificationShouldBeFound()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        arrayListDataBaseConnection.loadEntity(new SubEntity(idGenerator.generateId()));
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        final long minimalRequiredId = 3;
        final EntitySpecification<SubEntity> specification
                = (researchEntity) -> researchEntity.getId() >= minimalRequiredId;
        final List<SubEntity> matchingEntities = entityRepository.findMatchingEntities(specification);
        boolean testIsSuccess = true;
        for(final SubEntity matchingEntity : matchingEntities)
        {
            if(!specification.isMatch(matchingEntity))
            {
                testIsSuccess = false;
                break;
            }
        }
        Assert.assertTrue(testIsSuccess);
    }

    @Test
    public final void dataBaseShouldBeReloaded()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        final EntityRepository<SubEntity> entityRepository
            = new SubEntityRepository(arrayListDataBaseConnection);
        final List<SubEntity> entitiesOfRepository = new ArrayList<SubEntity>()
        {
            {
                this.add(new SubEntity(idGenerator.generateId()));
                this.add(new SubEntity(idGenerator.generateId()));
                this.add(new SubEntity(idGenerator.generateId()));
                this.add(new SubEntity(idGenerator.generateId()));
            }
        };
        for(final SubEntity newEntityOfRepository : entitiesOfRepository)
        {
            entityRepository.addEntity(newEntityOfRepository);
        }
        entityRepository.reloadDataBase();
        final int expectedAmountOfEntities = entitiesOfRepository.size();
        final int actualAmountOfEntities = entityRepository.findAmountOfEntities();
        Assert.assertEquals(actualAmountOfEntities, expectedAmountOfEntities);
    }

    @Test
    public final void amountOfEntitiesOfRepositoryShouldBeFound()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        final List<SubEntity> entitiesOfRepository = new ArrayList<SubEntity>()
        {
            {
                this.add(new SubEntity(idGenerator.generateId()));
                this.add(new SubEntity(idGenerator.generateId()));
                this.add(new SubEntity(idGenerator.generateId()));
                this.add(new SubEntity(idGenerator.generateId()));
            }
        };
        for(final SubEntity newEntityOfDataBase : entitiesOfRepository)
        {
            entityRepository.addEntity(newEntityOfDataBase);
        }
        final int expectedAmountOfEntitiesOfRepository = entitiesOfRepository.size();
        final int actualAmountOfEntitiesOfRepository = entityRepository.findAmountOfEntities();
        Assert.assertEquals(actualAmountOfEntitiesOfRepository, expectedAmountOfEntitiesOfRepository);
    }

    @Test
    public final void repositoryShouldBeIterated()
    {
        final IdGenerator idGenerator = new IdGenerator();
        final ArrayListDataBaseConnection<SubEntity> arrayListDataBaseConnection
                = new ArrayListDataBaseConnection<SubEntity>();
        final EntityRepository<SubEntity> entityRepository
                = new SubEntityRepository(arrayListDataBaseConnection);
        final List<SubEntity> entitiesOfRepository = new ArrayList<SubEntity>()
        {
            {
                this.add(new SubEntity(idGenerator.generateId()));
                this.add(new SubEntity(idGenerator.generateId()));
                this.add(new SubEntity(idGenerator.generateId()));
                this.add(new SubEntity(idGenerator.generateId()));
            }
        };
        for(final SubEntity newEntityOfDataBase : entitiesOfRepository)
        {
            entityRepository.addEntity(newEntityOfDataBase);
        }
        boolean testIsSuccess = true;
        for(final SubEntity entity : entityRepository)
        {
            if(!entitiesOfRepository.contains(entity))
            {
                testIsSuccess = false;
                break;
            }
        }
        Assert.assertTrue(testIsSuccess);
    }
}
