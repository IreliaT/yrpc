package com.ire;

import com.ire.common.ClassScanner;
import com.ire.common.reference.RpcReferenceScanner;
import com.ire.common.server.RpcServiceScanner;
import org.junit.Test;

import java.util.List;

/**
 * Hello world!
 *
 */
public class ScannerTest {

    @Test
    public void testScannerClassNameList() throws Exception {
        List<String> classNameList = ClassScanner.getClassNameList("com.ire.scanner.test", true);
        classNameList.forEach(System.out::println);
    }

    /**
     * 扫描io.binghe.rpc.test.scanner包下所有标注了@RpcService注解的类
     */
//    @Test
//    public void testScannerClassNameListByRpcService() throws Exception {
//         RpcServiceScanner.doScannerWithRpcServiceAnnotationFilterAndRegistryService("com.ire.scanner.test");
//    }

    /**
     * 扫描io.binghe.rpc.test.scanner包下所有标注了@RpcReference注解的类
     */
    @Test
    public void testScannerClassNameListByRpcReference() throws Exception {
        RpcReferenceScanner.doScannerWithRpcReferenceAnnotationFilter("com.ire.scanner.test");
    }
}
