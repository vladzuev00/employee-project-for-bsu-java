package by.bsu.zuevvlad.thirdlab.entityrepository.exception;

public final class EntityRepositorySearchingException extends EntityRepositoryException
{
    public EntityRepositorySearchingException()
    {
        super();
    }

    public EntityRepositorySearchingException(final String description)
    {
        super(description);
    }

    public EntityRepositorySearchingException(final Exception cause)
    {
        super(cause);
    }

    public EntityRepositorySearchingException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
