/*
 * @projectName xsxcrw
 * @package com.thunisoft.xsxcpt.xsxclc.zhfx.test
 * @className com.thunisoft.xsxcpt.xsxclc.zhfx.test.TestEverything
 * @copyright Copyright 2020 Thuisoft, Inc. All rights reserved.
 */
package com.thunisoft.xsxcpt.xsxclc.zhfx.test;

import com.thunisoft.ofd.operate.IOfdOperationAgent;
import com.thunisoft.ofd.operate.OfdOperation;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * TestEverything
 * @description TODO
 * @author chenjunqi
 * @date 2020年12月23日 13:50
 * @version 1.0.0
 */
public class TestEverything {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        IOfdOperationAgent operationAgent = new OfdOperation();
        List<String> strings = operationAgent.extractOfdText(new File("e:\\result.ofd"));
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void f() {
        IOfdOperationAgent operationAgent = new OfdOperation();
        List<String> contents = operationAgent.extractOfdPlainText(new File("e:\\ccc.ofd"));
        String s = contents.get(1);
        System.out.println(s);
        String[] split = s.split("\r\n");
        for (String string : split) {
            System.out.println(string);
        }
    }

    @Test
    public void g() {
        String c = "che，njunqi士大夫陈sd。fsfsdfsdf";
        int index = c.indexOf("大夫");
        int after = ++index;
        int before = --index;
        System.out.println(index);

        while (true) {
            if (after >= c.length() || c.charAt(after) == '。') {
                break;
            }
            after++;
        }

        //        while (true) {
        //            if (c.charAt(before) == '。' || c.charAt(before) == '，') {
        //                break;
        //            }
        //            before--;
        //        }
        System.out.println(c.substring(before + 1, after + 1));
    }

}
