/*
 * @projectName xsxcrw
 * @package com.thunisoft.xsxcpt.xsxclc.zhfx.test
 * @className com.thunisoft.xsxcpt.xsxclc.zhfx.test.RegTestNoThread
 * @copyright Copyright 2020 Thuisoft, Inc. All rights reserved.
 */
package com.thunisoft.xsxcpt.xsxclc.zhfx.test;

import com.thunisoft.ofd.operate.IOfdOperationAgent;
import com.thunisoft.ofd.operate.OfdOperation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegTestNoThread
 * @description TODO
 * @author chenjunqi
 * @date 2020年12月22日 17:19
 * @version 1.0.0
 */
public class RegTestNoThread {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        IOfdOperationAgent operationAgent = new OfdOperation();
        List<String> contents = operationAgent.extractOfdPlainText(new File("e:\\result.ofd"));
        long t1 = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        List<String> keywords = Keywords.keywords;
        for (String keyword : keywords) {
            String reg = "([^，。]+)" + keyword + ".+。";
            Pattern p = Pattern.compile(reg);
            for (String c : contents) {
                Matcher m = p.matcher(c);
                while (m.find()) {
                    list.add(keyword + "****" + m.group());
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
