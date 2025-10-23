package com.ire.common.id;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Date 2025/10/23 15:10
 */
public class IdFactory {

    private final static AtomicLong REQUEST_ID_GEN = new AtomicLong(0);

    public static Long getId(){
        return REQUEST_ID_GEN.incrementAndGet();
    }
}
