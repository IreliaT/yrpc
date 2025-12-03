package com.ire.spi.test;

import com.ire.rpc.spi.loader.ExtensionLoader;
import com.ire.spi.rpc.SPIService;
import org.junit.Test;

/**
 * @Date 2025/12/3 18:41
 */
public class SPITest {

    @Test
    public void testSpiLoader(){
        SPIService spiService = ExtensionLoader.getExtension(SPIService.class,"spiService");
        String aa = spiService.hello("AA");
        System.out.println(aa);
    }
}
