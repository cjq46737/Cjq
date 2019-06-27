package com.thunisoft.netRobot;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WyMusic {
    public static void printHot(String u) throws Exception{
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault() ;
        HttpPost httpPost = new HttpPost(u) ;
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");

        List<NameValuePair> list=new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("params","xBD1zU9Fnuf4W7tG6MmK6wEpszrF4vYg7koA8SRpKcep92c37EdAR8LJc+Y3spvtoIfkFK7mbCbWX7VHe4quSe0H1UrIkpz+mxLzfXkOq38fXxnnHsRt7TS0wU9k8yyN/8+WE7tsoCjEZzEUNOfm3H8iJDP8acQ16DNLfDhn5y6+c4PISE8vxAoEBx+HHhIj"));
        list.add(new BasicNameValuePair("encSecKey","53ce7109053f22ab93ed0f29a173b688cae04ee56df3afebd65423c2aadb8b08fbd3503c0eae5d815bbb43b95841f9e2bd2d5d09bf5da6c4824e60338306c8a37e9a15106942da7d41cbca623b22eee199be29a7d655348505e7df4182841095528319f35d8ca91ae7a367533b0492bc9cdd40296f8b451d2ebe60a8eb164168"));

        httpPost.setEntity(new UrlEncodedFormEntity(list));
        CloseableHttpResponse response=closeableHttpClient.execute(httpPost);

        HttpEntity entity=response.getEntity();
        String ux = EntityUtils.toString(entity,"utf-8") ;
        ArrayList<String> s= getBook(ux);

        for(int i=0;i<s.size();i++){
            String []arr = s.get(i).split("\"") ;
            System.out.println(arr[2]);
        }
    }
    public static void main(String[] args) throws Exception {
        String u = "https://music.163.com/weapi/v1/resource/comments/R_SO_4_254574?csrf_token=" ;
        printHot(u);
    }

    public static ArrayList<String> getBook(String read){
        ArrayList<String> arrayList = new ArrayList<String>() ;

        String con = "content(.*?)\"}" ;
        Pattern ah = Pattern.compile(con);
        Matcher mr = ah.matcher(read);
        while(mr.find()) {
            if (!arrayList.contains(mr.group())) {
                arrayList.add(mr.group());
            }
        }
        return  arrayList ;
    }
}