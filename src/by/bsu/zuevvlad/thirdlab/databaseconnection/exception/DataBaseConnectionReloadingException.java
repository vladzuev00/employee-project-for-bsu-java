package by.bsu.zuevvlad.thirdlab.databaseconnection.exception;

public class DataBaseConnectionReloadingException extends DataBaseConnectionException
{
    public DataBaseConnectionReloadingException()
    {
        super();
    }

    public DataBaseConnectionReloadingException(final String description)
    {
        super(description);
    }

    public DataBaseConnectionReloadingException(final Exception cause)
    {
        super(cause);
    }

    public DataBaseConnectionReloadingException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
