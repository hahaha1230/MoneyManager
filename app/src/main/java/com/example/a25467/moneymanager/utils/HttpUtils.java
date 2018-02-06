package com.example.a25467.moneymanager.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/2/6 0006.
 */

public class HttpUtils {

    public static String getUrl(String code){
        return new StringBuffer("http://tj.nineton.cn/Heart/index/all")
                .append("?city=").append(code).toString();
    }

    public static String get(String url){
        try {
            URL getUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) getUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int code = conn.getResponseCode();
            //状态码是200说明成功
            if (code == 200) {
                // 从流中读取响应信息
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));

                String line;
                StringBuffer msg = new StringBuffer();

                while ((line = reader.readLine()) != null) { // 循环从流中读取
                    msg.append(line).append( "\n");
                }
                reader.close(); // 关闭流

                return msg.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
