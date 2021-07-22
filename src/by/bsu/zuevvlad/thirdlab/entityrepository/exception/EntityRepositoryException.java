package by.bsu.zuevvlad.thirdlab.entityrepository.exception;

public class EntityRepositoryException extends RuntimeException
{
    public EntityRepositoryException()
    {
        super();
    }

    public EntityRepositoryException(final String description)
    {
        super(description);
    }

    public EntityRepositoryException(final Exception cause)
    {
        super(cause);
    }

    public EntityRepositoryException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
