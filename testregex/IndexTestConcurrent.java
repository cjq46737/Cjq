/*
 * @projectName xsxcrw
 * @package com.thunisoft.xsxcpt.xsxclc.zhfx.test
 * @className com.thunisoft.xsxcpt.xsxclc.zhfx.test.IndexTestConcurrent
 * @copyright Copyright 2020 Thuisoft, Inc. All rights reserved.
 */
package com.thunisoft.xsxcpt.xsxclc.zhfx.test;

import com.thunisoft.ofd.operate.IOfdOperationAgent;
import com.thunisoft.ofd.operate.OfdOperation;

import java.io.File;
import java.util.List;
import java.util.concurrent.*;

/**
 * IndexTestConcurrent
 * @description TODO
 * @author chenjunqi
 * @date 2020年12月23日 15:02
 * @version 1.0.0
 */
public class IndexTestConcurrent {

    private final static ThreadPoolExecutor executors = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1,
            Runtime.getRuntime().availableProcessors() * 2, 30, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10000),
            r -> new Thread(r, "clfx-" + r.hashCode()), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws Exception {

        List<String> keywords = Keywords.keywords;

        long start = System.currentTimeMillis();
        IOfdOperationAgent operationAgent = new OfdOperation();
        List<String> contents = operationAgent.extractOfdText(new File("e:\\result.ofd"));

        long t1 = System.currentTimeMillis();
        CopyOnWriteArrayList<String> result = new CopyOnWriteArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(keywords.size());

        for (String keyword : keywords) {
            executors.execute(new IndexTestConcurrent.RegTask(keyword, result, countDownLatch, contents));
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

                for (String c : content) {
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
            } finally {
                count.countDown();
            }
        }
    }
}
