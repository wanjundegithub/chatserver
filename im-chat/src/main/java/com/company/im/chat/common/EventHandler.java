package com.company.im.chat.common;


import java.lang.annotation.*;

/**
 * 事件处理者
 *
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

    /**
     * 绑定的事件类型列表
     */
    EventType[] value();

}
