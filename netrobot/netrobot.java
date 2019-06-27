package com.thunisoft.netRobot;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetRobot {
	// 提取的文件放在这个目录
	private static String path = "e:\\dangdang\\";

	// 存储需要爬取的链接
	private static List<String> allWaitUrl = new ArrayList<>();

	// 已经爬取过的链接
	private static Set<String> allOverUrl = new HashSet<>();

	// 所有url深度进行判断
	private static Map<String, Integer> allUrlDepth = new HashMap<>();

	// 最大爬取深度
	private static int maxDepth = 3;

	public static void main(String[] args) {
		String url = "https://music.163.com/";

		workurl(url, 1);
	}

	public static void workurl(String strurl, int depth) {
		// 判断当前url是否爬取过
		if (!(allOverUrl.contains(strurl) || depth > maxDepth)) {
			// 建立url爬取核心对象
			try {
				URL url = new URL(strurl);
				// 通过url建立与网页的连接
				URLConnection conn = url.openConnection();
				// 通过链接取得网页返回的数据
				InputStream is = conn.getInputStream();

				// 一般按行读取网页数据，并进行内容分析
				// 因此用BufferedReader和InputStreamReader把字节流转化为字符流的缓冲流
				// 进行转换时，需要处理编码格式问题
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));

				// 按行读取并打印
				String line = null;
				// 正则表达式的匹配规则提取该网页的链接
				Pattern p = Pattern.compile("<a .*href=.+</a>");
				// 建立一个输出流，用于保存文件,文件名为执行时间，以防重复
				PrintWriter pw = new PrintWriter(new File(path+ System.currentTimeMillis() + ".txt"));

				while ((line = br.readLine()) != null) {
					// System.out.println(line);
					// 编写正则，匹配超链接地址
					pw.println(line);
					Matcher m = p.matcher(line);
					while (m.find()) {
						String href = m.group();
						// 找到超链接地址并截取字符串
						// 有无引号
						href = href.substring(href.indexOf("href="));
						if (href.charAt(5) == '\"') {
							href = href.substring(6);
						} else {
							href = href.substring(5);
						}
						// 截取到引号或者空格或者到">"结束
						try {
							href = href.substring(0, href.indexOf("\""));
						} catch (Exception e) {
							try {
								href = href.substring(0, href.indexOf(" "));
							} catch (Exception e1) {
								href = href.substring(0, href.indexOf(">"));
							}
						}
						if (href.startsWith("http:")|| href.startsWith("https:")) {
							// 输出该网页存在的链接
							// System.out.println(href);
							// 将url地址放到队列中
							allWaitUrl.add(href);
							allUrlDepth.put(href, depth + 1);
						}else if(href.startsWith("//")){
							href ="http://" + href.substring(2);
							allWaitUrl.add(href);
							allUrlDepth.put(href, depth + 1);
						}
					}
				}
				pw.close();
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 将当前url归列到allOverUrl中
			allOverUrl.add(strurl);
			System.out.println(strurl + "网页爬取完成，已爬取数量：" + allOverUrl.size()
					+ "，剩余爬取数量：" + allWaitUrl.size());
		}
		// 用递归的方法继续爬取其他链接
		if(allWaitUrl == null || allWaitUrl.size() == 0){
			return;
		}
		String nexturl = allWaitUrl.get(0);
		allWaitUrl.remove(0);
		workurl(nexturl, allUrlDepth.get(nexturl));
	}
}
