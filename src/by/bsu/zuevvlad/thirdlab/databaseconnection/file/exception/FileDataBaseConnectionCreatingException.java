package by.bsu.zuevvlad.thirdlab.databaseconnection.file.exception;

import by.bsu.zuevvlad.thirdlab.databaseconnection.exception.DataBaseConnectionCreatingException;

public final class FileDataBaseConnectionCreatingException extends DataBaseConnectionCreatingException
{
    public FileDataBaseConnectionCreatingException()
    {
        super();
    }

    public FileDataBaseConnectionCreatingException(final String description)
    {
        super(description);
    }

    public FileDataBaseConnectionCreatingException(final Exception cause)
    {
        super(cause);
    }

    public FileDataBaseConnectionCreatingException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}

