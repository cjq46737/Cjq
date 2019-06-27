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
	// ��ȡ���ļ��������Ŀ¼
	private static String path = "e:\\dangdang\\";

	// �洢��Ҫ��ȡ������
	private static List<String> allWaitUrl = new ArrayList<>();

	// �Ѿ���ȡ��������
	private static Set<String> allOverUrl = new HashSet<>();

	// ����url��Ƚ����ж�
	private static Map<String, Integer> allUrlDepth = new HashMap<>();

	// �����ȡ���
	private static int maxDepth = 3;

	public static void main(String[] args) {
		String url = "https://music.163.com/";

		workurl(url, 1);
	}

	public static void workurl(String strurl, int depth) {
		// �жϵ�ǰurl�Ƿ���ȡ��
		if (!(allOverUrl.contains(strurl) || depth > maxDepth)) {
			// ����url��ȡ���Ķ���
			try {
				URL url = new URL(strurl);
				// ͨ��url��������ҳ������
				URLConnection conn = url.openConnection();
				// ͨ������ȡ����ҳ���ص�����
				InputStream is = conn.getInputStream();

				// һ�㰴�ж�ȡ��ҳ���ݣ����������ݷ���
				// �����BufferedReader��InputStreamReader���ֽ���ת��Ϊ�ַ����Ļ�����
				// ����ת��ʱ����Ҫ��������ʽ����
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));

				// ���ж�ȡ����ӡ
				String line = null;
				// ������ʽ��ƥ�������ȡ����ҳ������
				Pattern p = Pattern.compile("<a .*href=.+</a>");
				// ����һ������������ڱ����ļ�,�ļ���Ϊִ��ʱ�䣬�Է��ظ�
				PrintWriter pw = new PrintWriter(new File(path+ System.currentTimeMillis() + ".txt"));

				while ((line = br.readLine()) != null) {
					// System.out.println(line);
					// ��д����ƥ�䳬���ӵ�ַ
					pw.println(line);
					Matcher m = p.matcher(line);
					while (m.find()) {
						String href = m.group();
						// �ҵ������ӵ�ַ����ȡ�ַ���
						// ��������
						href = href.substring(href.indexOf("href="));
						if (href.charAt(5) == '\"') {
							href = href.substring(6);
						} else {
							href = href.substring(5);
						}
						// ��ȡ�����Ż��߿ո���ߵ�">"����
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
							// �������ҳ���ڵ�����
							// System.out.println(href);
							// ��url��ַ�ŵ�������
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
			// ����ǰurl���е�allOverUrl��
			allOverUrl.add(strurl);
			System.out.println(strurl + "��ҳ��ȡ��ɣ�����ȡ������" + allOverUrl.size()
					+ "��ʣ����ȡ������" + allWaitUrl.size());
		}
		// �õݹ�ķ���������ȡ��������
		if(allWaitUrl == null || allWaitUrl.size() == 0){
			return;
		}
		String nexturl = allWaitUrl.get(0);
		allWaitUrl.remove(0);
		workurl(nexturl, allUrlDepth.get(nexturl));
	}
}
