/*
 * @projectName xsxcrw
 * @package com.thunisoft.xsxcpt.xsxclc.zhfx.test
 * @className com.thunisoft.xsxcpt.xsxclc.zhfx.test.TestKeywordsTimes
 * @copyright Copyright 2020 Thuisoft, Inc. All rights reserved.
 */
package com.thunisoft.xsxcpt.xsxclc.zhfx.test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.thunisoft.ofd.operate.IOfdOperationAgent;
import com.thunisoft.ofd.operate.OfdOperation;

import java.io.File;
import java.util.List;

/**
 * TestKeywordsTimes
 * @description TODO
 * @author chenjunqi
 * @date 2020年12月23日 10:58
 * @version 1.0.0
 */
public class TestKeywordsTimes {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        IOfdOperationAgent operationAgent = new OfdOperation();
        List<String> contents = operationAgent.extractOfdPlainText(new File("e:\\result.ofd"));
        long ofd = System.currentTimeMillis();

        Multimap<String, Integer> objectObjectArrayListMultimap = ArrayListMultimap.create();
        List<String> keywords = Keywords.keywords;

        for (String keyword : keywords) {
            for (int i = 0; i < contents.size(); i++) {
                if (contents.get(i).contains(keyword)) {
                    objectObjectArrayListMultimap.put(keyword, i);
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("总体时间：" + (end - start));
        System.out.println("ofd时间：" + (ofd - start));
        System.out.println("keyword times：" + (end - ofd));
        //
        //        for (String key : objectObjectArrayListMultimap.keySet()) {
        //            System.out.println(key + "***" + objectObjectArrayListMultimap.get(key));
        //        }
        //        Set<String> key = new HashSet<>(keywords);
        //        key.removeAll(objectObjectArrayListMultimap.keySet());
        //        System.out.println(key);
    }
}
