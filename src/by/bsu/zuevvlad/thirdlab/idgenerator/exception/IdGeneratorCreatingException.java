package by.bsu.zuevvlad.thirdlab.idgenerator.exception;

public final class IdGeneratorCreatingException extends IdGeneratorException
{
    public IdGeneratorCreatingException()
    {
        super();
    }

    public IdGeneratorCreatingException(final String description)
    {
        super(description);
    }

    public IdGeneratorCreatingException(final Exception cause)
    {
        super(cause);
    }

    public IdGeneratorCreatingException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
