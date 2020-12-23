/*
 * @projectName xsxcrw
 * @package com.thunisoft.xsxcpt.xsxclc.zhfx.test
 * @className com.thunisoft.xsxcpt.xsxclc.zhfx.test.IndexTestNoThread
 * @copyright Copyright 2020 Thuisoft, Inc. All rights reserved.
 */
package com.thunisoft.xsxcpt.xsxclc.zhfx.test;

import com.thunisoft.ofd.operate.IOfdOperationAgent;
import com.thunisoft.ofd.operate.OfdOperation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * IndexTestNoThread
 * @description TODO
 * @author chenjunqi
 * @date 2020年12月23日 15:48
 * @version 1.0.0
 */
public class IndexTestNoThread {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        IOfdOperationAgent operationAgent = new OfdOperation();
        List<String> contents = operationAgent.extractOfdText(new File("e:\\result.ofd"));
        long t1 = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        List<String> keywords = Keywords.keywords;
        for (String keyword : keywords) {
            for (String c : contents) {
                int index = c.indexOf(keyword);
                int after = ++index;
                int before = --index;
                if (index != -1) {
                    while (true) {
                        if (after >= c.length()) {
                            break;
                        }
                        char temp = c.charAt(after);
                        if (temp == '。' || temp == '\r' || temp == '\n') {
                            break;
                        }
                        after++;
                    }

                    while (true) {
                        char temp = c.charAt(before);
                        if (before <= 0 || temp == '。' || temp == '，' || temp == '\r' || temp == '\n') {
                            break;
                        }
                        before--;
                    }
                    if (after == c.length()) {
                        list.add(keyword + "****" + c.substring(before + 1, after));
                    } else {
                        list.add(keyword + "****" + c.substring(before + 1, after + 1));
                    }
                }
            }
        }

        long end = System.currentTimeMillis();
        list.forEach(System.out::println);
        System.out.println("消耗总时间：" + (end - start));
        System.out.println("分析消耗时间：" + (end - t1));
        System.out.println("ofd获取内容消耗时间：" + (t1 - start));
        System.out.println(list.size());
    }

}
