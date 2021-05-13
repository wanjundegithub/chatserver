package com.company.im.chat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {

    private static Logger getLogger(Class clazz) {
        Logger logger=LoggerFactory.getLogger(clazz);
        return logger;
    }

    public static void info(String message,Class clazz){
        getLogger(clazz).info(message);
    }

    public static void error(String error,Class clazz){
        getLogger(clazz).error(error);
    }
}
