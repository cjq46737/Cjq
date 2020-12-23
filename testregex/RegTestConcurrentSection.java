/*
 * @projectName xsxcrw
 * @package com.thunisoft.xsxcpt.xsxclc.zhfx.test
 * @className com.thunisoft.xsxcpt.xsxclc.zhfx.test.RegTestConcurrentSection
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
 * RegTestConcurrentSection
 * @description TODO
 * @author chenjunqi
 * @date 2020年12月23日 14:37
 * @version 1.0.0
 */
public class RegTestConcurrentSection {

    private final static ThreadPoolExecutor executors = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1,
            Runtime.getRuntime().availableProcessors() * 2, 30, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10000),
            r -> new Thread(r, "clfx-" + r.hashCode()), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws Exception {
        Map<String, Pattern> patternMap = new HashMap<>();
        List<String> keywords = Keywords.keywords;
        keywords.forEach(item -> {
            String reg = "([^，。]+)" + item + ".+。";
            Pattern p = Pattern.compile(reg);
            patternMap.put(item, p);
        });

        long start = System.currentTimeMillis();
        IOfdOperationAgent operationAgent = new OfdOperation();
        List<String> contents = operationAgent.extractOfdPlainText(new File("e:\\result.ofd"));
        long t1 = System.currentTimeMillis();

        Map<Integer, String[]> contentMap = new HashMap<>();
        for (int i = 0; i < contents.size(); i++) {
            String[] split = contents.get(i).split("\r\n");
            contentMap.put(i, split);
        }

        CopyOnWriteArrayList<String> result = new CopyOnWriteArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(keywords.size());

        for (String keyword : keywords) {
            executors.execute(new RegTestConcurrentSection.RegTask(keyword, result, countDownLatch, contentMap, patternMap));
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

        private final Map<Integer, String[]> content;

        private final Map<String, Pattern> map;

        public RegTask(String keyword, List<String> list, CountDownLatch count, Map<Integer, String[]> content, Map<String, Pattern> map) {
            this.keyword = keyword;
            this.list = list;
            this.count = count;
            this.content = content;
            this.map = map;
        }

        @Override
        public void run() {
            try {
                for (String[] section : content.values()) {
                    for (String s : section) {
                        if (s.contains(keyword)) {
                            Matcher m = map.get(keyword).matcher(s);
                            while (m.find()) {
                                list.add(keyword + "****" + m.group());
                            }
                        }
                    }
                }

            } finally {
                count.countDown();
            }

        }
    }
}
