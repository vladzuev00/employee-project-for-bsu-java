package by.bsu.zuevvlad.thirdlab.daemonthreadfactory;

import java.util.concurrent.ThreadFactory;

public final class DaemonThreadFactory implements ThreadFactory
{
    @Override
    public final Thread newThread(final Runnable runnable)
    {
        final Thread createdThread = new Thread(runnable);
        createdThread.setDaemon(true);
        return createdThread;
    }
}
