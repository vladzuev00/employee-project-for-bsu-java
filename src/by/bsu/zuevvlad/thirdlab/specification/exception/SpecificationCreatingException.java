package by.bsu.zuevvlad.thirdlab.specification.exception;

public final class SpecificationCreatingException extends SpecificationException
{
    public SpecificationCreatingException()
    {
        super();
    }

    public SpecificationCreatingException(final String description)
    {
        super(description);
    }

    public SpecificationCreatingException(final Exception cause)
    {
        super(cause);
    }

    public SpecificationCreatingException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
