package by.bsu.zuevvlad.thirdlab.databaseconnection.file.exception;

import by.bsu.zuevvlad.thirdlab.databaseconnection.exception.DataBaseConnectionOffloadingException;

public final class FileDataBaseConnectionOffloadingException extends DataBaseConnectionOffloadingException
{
    public FileDataBaseConnectionOffloadingException()
    {
        super();
    }

    public FileDataBaseConnectionOffloadingException(final String description)
    {
        super(description);
    }

    public FileDataBaseConnectionOffloadingException(final Exception cause)
    {
        super(cause);
    }

    public FileDataBaseConnectionOffloadingException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
