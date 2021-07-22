package by.bsu.zuevvlad.thirdlab.databaseconnection.exception;

public class DataBaseConnectionException extends Exception
{
    public DataBaseConnectionException()
    {
        super();
    }

    public DataBaseConnectionException(final String description)
    {
        super(description);
    }

    public DataBaseConnectionException(final Exception cause)
    {
        super(cause);
    }

    public DataBaseConnectionException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
