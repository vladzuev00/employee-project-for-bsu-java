package by.bsu.zuevvlad.thirdlab.entityrepository.exception;

public final class EntityRepositoryReloadingDataBaseException extends EntityRepositoryException
{
    public EntityRepositoryReloadingDataBaseException()
    {
        super();
    }

    public EntityRepositoryReloadingDataBaseException(final String description)
    {
        super(description);
    }

    public EntityRepositoryReloadingDataBaseException(final Exception cause)
    {
        super(cause);
    }

    public EntityRepositoryReloadingDataBaseException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
