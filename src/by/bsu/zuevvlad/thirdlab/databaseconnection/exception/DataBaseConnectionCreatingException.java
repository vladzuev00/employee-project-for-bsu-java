package by.bsu.zuevvlad.thirdlab.databaseconnection.exception;

public class DataBaseConnectionCreatingException extends DataBaseConnectionException
{
    public DataBaseConnectionCreatingException()
    {
        super();
    }

    public DataBaseConnectionCreatingException(final String description)
    {
        super(description);
    }

    public DataBaseConnectionCreatingException(final Exception cause)
    {
        super(cause);
    }

    public DataBaseConnectionCreatingException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
