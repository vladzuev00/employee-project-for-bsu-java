package by.bsu.zuevvlad.thirdlab.idgenerator;

import by.bsu.zuevvlad.thirdlab.idgenerator.exception.IdGeneratorCreatingException;
import by.bsu.zuevvlad.thirdlab.validator.entity.ValidatorForPropertiesOfEntity;

public final class IdGenerator
{
    private long nextGeneratedId;

    public IdGenerator()
    {
        this.nextGeneratedId = IdGenerator.DEFAULT_INITIAL_NEXT_GENERATED_ID;
    }

    private static final long DEFAULT_INITIAL_NEXT_GENERATED_ID = 0;

    public IdGenerator(final long nextGeneratedId)
    {
        if(!IdGenerator.VALIDATOR_FOR_PROPERTIES_OF_ENTITY.isValidId(nextGeneratedId))
        {
            throw new IdGeneratorCreatingException("Impossible to create object of class '"
                    + this.getClass().getName() + "' with given not valid initial next generated "
                    + "id. Given initial next generated id: " + nextGeneratedId + ".");
        }
        this.nextGeneratedId = nextGeneratedId;
    }

    private static final ValidatorForPropertiesOfEntity VALIDATOR_FOR_PROPERTIES_OF_ENTITY
            = new ValidatorForPropertiesOfEntity();

    public final long getNextGeneratedId()
    {
        return this.nextGeneratedId;
    }

    public final long generateId()
    {
        return this.nextGeneratedId++;
    }
}
