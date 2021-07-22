package by.bsu.zuevvlad.thirdlab.databaseconnection.file.exception;

import by.bsu.zuevvlad.thirdlab.databaseconnection.exception.DataBaseConnectionReloadingException;

public final class FileDataBaseConnectionReloadingException extends DataBaseConnectionReloadingException
{
    public FileDataBaseConnectionReloadingException()
    {
        super();
    }

    public FileDataBaseConnectionReloadingException(final String description)
    {
        super(description);
    }

    public FileDataBaseConnectionReloadingException(final Exception cause)
    {
        super(cause);
    }

    public FileDataBaseConnectionReloadingException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
