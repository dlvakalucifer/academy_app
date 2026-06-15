package dev.wegner.academy_app.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LogCategories
{
    private LogCategories()
    {
    }

    public static final Logger SECURITY =
            LoggerFactory.getLogger("security");

    public static final Logger STUDENT =
            LoggerFactory.getLogger("student");

    public static final Logger STORAGE =
            LoggerFactory.getLogger("storage");
}