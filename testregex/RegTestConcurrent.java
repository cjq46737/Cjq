/*
 * @projectName xsxcrw
 * @package com.thunisoft.xsxcpt.xsxclc.zhfx.dto
 * @className com.thunisoft.xsxcpt.xsxclc.zhfx.test.RegTest
 * @copyright Copyright 2020 Thuisoft, Inc. All rights reserved.
 */
package com.thunisoft.xsxcpt.xsxclc.zhfx.test;

import com.thunisoft.ofd.operate.IOfdOperationAgent;
import com.thunisoft.ofd.operate.OfdOperation;

import java.io.File;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegTest
 * @description TODO
 * @author chenjunqi
 * @date 2020年12月22日 14:02
 * @version 1.0.0
 */
public class RegTestConcurrent {

    private final static ThreadPoolExecutor executors = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1,
            Runtime.getRuntime().availableProcessors() * 2, 30, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10000),
            r -> new Thread(r, "clfx-" + r.hashCode()), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        IOfdOperationAgent operationAgent = new OfdOperation();
        List<String> contents = operationAgent.extractOfdPlainText(new File("e:\\result.ofd"));

        long t1 = System.currentTimeMillis();
        CopyOnWriteArrayList<String> result = new CopyOnWriteArrayList<>();
        List<String> keywords = Keywords.keywords;
        CountDownLatch countDownLatch = new CountDownLatch(keywords.size());

        for (String keyword : keywords) {
            executors.execute(new RegTask(keyword, result, countDownLatch, contents));
        }

        countDownLatch.await();
        System.out.println(countDownLatch.getCount());
        long end = System.currentTimeMillis();
        result.forEach(System.out::println);
        System.out.println("消耗总时间：" + (end - start));
        System.out.println("分析消耗时间：" + (end - t1));
        System.out.println("ofd获取内容消耗时间：" + (t1 - start));
        System.out.println(result.size());

    }

    static class RegTask implements Runnable {

        private final String keyword;

        private final List<String> list;

        private final CountDownLatch count;

        private final List<String> content;

        public RegTask(String keyword, List<String> list, CountDownLatch count, List<String> content) {
            this.keyword = keyword;
            this.list = list;
            this.count = count;
            this.content = content;
        }

        @Override
        public void run() {
            try {
                //                ([^，]+)身份，.*。
                String reg = "([^，。]+)" + keyword + ".+。";
                Pattern p = Pattern.compile(reg);
                for (String c : content) {
                    Matcher m = p.matcher(c);
                    while (m.find()) {
                        list.add(keyword + "****" + m.group());
                    }
                }
            } finally {
                count.countDown();
            }

        }
    }
}
