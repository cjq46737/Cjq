/*
 * @projectName xsxcrw
 * @package com.thunisoft.xsxcpt.xsxclc.zhfx.test
 * @className com.thunisoft.xsxcpt.xsxclc.zhfx.test.RegTestTwiceConcurrent
 * @copyright Copyright 2020 Thuisoft, Inc. All rights reserved.
 */
package com.thunisoft.xsxcpt.xsxclc.zhfx.test;

import com.thunisoft.ofd.operate.IOfdOperationAgent;
import com.thunisoft.ofd.operate.OfdOperation;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegTestTwiceConcurrent
 * @description TODO
 * @author chenjunqi
 * @date 2020年12月22日 18:06
 * @version 1.0.0
 */
public class RegTestTwiceConcurrent {

    private final static ThreadPoolExecutor executors = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1,
            Runtime.getRuntime().availableProcessors() * 2, 30, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10000),
            r -> new Thread(r, "clfx-" + r.hashCode()), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws Exception {
        Map<String, Pattern> map = new HashMap<>();
        List<String> keywords = Keywords.keywords;
        keywords.forEach(item -> {
            String reg = "([^，。]+)" + item + ".+。";
            //            String reg = ".{5}" + item + ".+。";
            Pattern p = Pattern.compile(reg);
            map.put(item, p);
        });

        long start = System.currentTimeMillis();
        IOfdOperationAgent operationAgent = new OfdOperation();
        List<String> contents = operationAgent.extractOfdPlainText(new File("e:\\result.ofd"));

        long t1 = System.currentTimeMillis();
        CopyOnWriteArrayList<String> result = new CopyOnWriteArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(keywords.size());

        for (String keyword : keywords) {
            executors.execute(new RegTestTwiceConcurrent.RegTask(keyword, result, countDownLatch, contents, map));
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

        private final Map<String, Pattern> map;

        public RegTask(String keyword, List<String> list, CountDownLatch count, List<String> content, Map<String, Pattern> map) {
            this.keyword = keyword;
            this.list = list;
            this.count = count;
            this.content = content;
            this.map = map;
        }

        @Override
        public void run() {
            try {
                //                //                ([^，]+)身份，.*。
                //                String reg = "([^，。]+)" + keyword + ".+。";
                //                Pattern p = Pattern.compile(reg);
                for (String c : content) {
                    if (c.contains(keyword)) {
                        Matcher m = map.get(keyword).matcher(c);
                        while (m.find()) {
                            list.add(keyword + "****" + m.group());
                        }
                    }

                }
            } finally {
                count.countDown();
            }

        }
    }
}
