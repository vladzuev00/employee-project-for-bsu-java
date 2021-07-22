package by.bsu.zuevvlad.thirdlab.specification.exception;

public class SpecificationException extends RuntimeException
{
    public SpecificationException()
    {
        super();
    }

    public SpecificationException(final String description)
    {
        super(description);
    }

    public SpecificationException(final Exception cause)
    {
        super(cause);
    }

    public SpecificationException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
