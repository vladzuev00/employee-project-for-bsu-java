package by.bsu.zuevvlad.thirdlab.entityrepository.exception;

public class EntityRepositoryCreatingException extends EntityRepositoryException
{
    public EntityRepositoryCreatingException()
    {
        super();
    }

    public EntityRepositoryCreatingException(final String description)
    {
        super(description);
    }

    public EntityRepositoryCreatingException(final Exception cause)
    {
        super(cause);
    }

    public EntityRepositoryCreatingException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
