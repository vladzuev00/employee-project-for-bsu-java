package by.bsu.zuevvlad.thirdlab.databaseconnection.file.exception;

import by.bsu.zuevvlad.thirdlab.databaseconnection.exception.DataBaseConnectionLoadingException;

public final class FileDataBaseConnectionLoadingException extends DataBaseConnectionLoadingException
{
    public FileDataBaseConnectionLoadingException()
    {
        super();
    }

    public FileDataBaseConnectionLoadingException(final String description)
    {
        super(description);
    }

    public FileDataBaseConnectionLoadingException(final Exception cause)
    {
        super(cause);
    }

    public FileDataBaseConnectionLoadingException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
