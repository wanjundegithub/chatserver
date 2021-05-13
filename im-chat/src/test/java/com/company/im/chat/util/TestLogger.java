package com.company.im.chat.util;

import com.company.im.chat.utils.LoggerUtil;
import org.junit.Test;

public class TestLogger {

    @Test
    public void testLogger(){
        LoggerUtil.info("Hello,world",TestLogger.class);
    }

    @Test
    public void testException(){
        LoggerUtil.error("error",TestLogger.class);
    }
}
