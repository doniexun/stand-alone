package cn.savor.standalone.log.command.createfile.service;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by gy on 2017/10/26.
 */
public class GetItemList {

    public static JSONObject getItemList(String hotelId, String ur) {
        JSONObject jsonObject = null;
        boolean result = false;
        try {
            String url = ur + "=" + hotelId;
            String json = getHttpResponse(url);
            jsonObject = JSONObject.fromObject(json);
//            System.out.println(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public static String getHttpResponse(String allConfigUrl) {
        BufferedReader in = null;
        StringBuffer result = null;
        try {

            URI uri = new URI(allConfigUrl);
            URL url = uri.toURL();
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Charset", "utf-8");

            connection.connect();

            result = new StringBuffer();
            //读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
