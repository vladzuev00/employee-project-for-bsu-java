package by.bsu.zuevvlad.thirdlab.specification.exception;

public final class SpecificationModificationException extends SpecificationException
{
    public SpecificationModificationException()
    {
        super();
    }

    public SpecificationModificationException(final String description)
    {
        super(description);
    }

    public SpecificationModificationException(final Exception cause)
    {
        super(cause);
    }

    public SpecificationModificationException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
