package by.bsu.zuevvlad.thirdlab.entityrepository.exception;

public final class EntityRepositoryModificationException extends EntityRepositoryException
{
    public EntityRepositoryModificationException()
    {
        super();
    }

    public EntityRepositoryModificationException(final String description)
    {
        super(description);
    }

    public EntityRepositoryModificationException(final Exception cause)
    {
        super(cause);
    }

    public EntityRepositoryModificationException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
