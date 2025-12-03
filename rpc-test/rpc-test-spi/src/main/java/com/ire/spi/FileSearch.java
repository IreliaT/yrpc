package com.ire.spi;

import java.util.ServiceLoader;

/**
 * @Date 2025/12/3 17:45
 */
public class FileSearch implements Search{
    @Override
    public void search(String key) {
        System.out.println("文件" + key);
    }

    public static void main(String[] args) {
        ServiceLoader<Search> load = ServiceLoader.load(Search.class);
        for (Search next : load) {
            next.search("HX");
        }
    }
}
