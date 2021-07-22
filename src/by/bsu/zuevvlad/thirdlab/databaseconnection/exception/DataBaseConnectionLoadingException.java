package by.bsu.zuevvlad.thirdlab.databaseconnection.exception;

public class DataBaseConnectionLoadingException extends DataBaseConnectionException
{
    public DataBaseConnectionLoadingException()
    {
        super();
    }

    public DataBaseConnectionLoadingException(final String description)
    {
        super(description);
    }

    public DataBaseConnectionLoadingException(final Exception cause)
    {
        super(cause);
    }

    public DataBaseConnectionLoadingException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
