package com.ire.spi;

/**
 * @Date 2025/12/3 17:45
 */
public class DataSearch implements Search{
    @Override
    public void search(String key) {
        System.out.println("数据" + key);
    }
}
