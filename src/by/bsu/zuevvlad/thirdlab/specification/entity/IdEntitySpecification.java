package by.bsu.zuevvlad.thirdlab.specification.entity;


import by.bsu.zuevvlad.thirdlab.entity.Entity;
import by.bsu.zuevvlad.thirdlab.specification.exception.SpecificationCreatingException;
import by.bsu.zuevvlad.thirdlab.validator.entity.ValidatorForPropertiesOfEntity;

public final class IdEntitySpecification implements EntitySpecification<Entity>
{
    private long requiredId;

    public IdEntitySpecification()
    {
        super();
        this.requiredId = IdEntitySpecification.VALUE_OF_NOT_DEFINED_REQUIRED_ID;
    }

    private static final long VALUE_OF_NOT_DEFINED_REQUIRED_ID = Entity.VALUE_OF_NOT_DEFINED_ID;

    public IdEntitySpecification(final long requiredId)
    {
        if(!IdEntitySpecification.VALIDATOR_FOR_PROPERTIES_OF_ENTITY.isValidId(requiredId))
        {
            throw new SpecificationCreatingException("Impossible to create object-specification of class '"
                    + this.getClass().getName() + "' with given not valid required id. Given required id: "
                    + requiredId + ".");
        }
        this.requiredId = requiredId;
    }

    private static final ValidatorForPropertiesOfEntity VALIDATOR_FOR_PROPERTIES_OF_ENTITY
            = new ValidatorForPropertiesOfEntity();

    public final void setRequiredId(final long requiredId)
    {
        if(!IdEntitySpecification.VALIDATOR_FOR_PROPERTIES_OF_ENTITY.isValidId(requiredId))
        {
            throw new SpecificationCreatingException("Impossible to update object-specification of class '"
                    + this.getClass().getName() + "' by given nit valid required id. Given required id: "
                    + requiredId + ".");
        }
        this.requiredId = requiredId;
    }

    public final long getRequiredId()
    {
        return this.requiredId;
    }

    @Override
    public final boolean isMatch(final Entity researchEntity)
    {
        final long idOfResearchEntity = researchEntity.getId();
        return this.requiredId == idOfResearchEntity;
    }
}
