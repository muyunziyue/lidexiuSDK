package com.ldx;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @ClassName LogEngineLSdk
 * @Description 后台收集日志sdk
 * @Author Lidexiu
 * @Date 2018/8/15 9:47
 * @Version 1.0
 **/
public class LogEngineLSdk {

    // 获取日志打印对象
    private final static Logger logger =Logger.getGlobal();

    private final static String access_url = "http://192.168.216.111/index.html";
    private final static String version = "1.0"; //版本号
    private final static String platformName = "java_server";
    private final static String sdkName = "java_sdk";

    /**
     * 支付成功或者退款事件,
     * @param oid
     * @param mid
     * @param flag  cr: 退款成功    cs: 支付成功
     * @return  返回true: 支付成功, false: 支付失败
     */
    public static boolean Charge(String oid, String mid, String flag) {
        try {
            if (isEmpty(oid) || isEmpty(mid)){
                logger.log(Level.WARNING, "oid&mid may be null.oid"+oid+" mid" + mid);
                return false;
            }
            // 正常支付 构建url: http://192.168.216.111/index.html?en=e_cs&ver=1.0&pl=java_server
            Map<String, String> info = new HashMap<>();
            // 将需要的参数添加到info中
            info.put("u_mid", mid);
            info.put("oid", oid);
            info.put("ver", version);
            if(flag.equals("cs")){
                info.put("en", "e_cs");
            }else {
                info.put("en", "e_cr");
            }
            info.put("platformName", platformName);
            info.put("sdk", sdkName);

            // 构建url
            String url = buildUrl(info);

            //TODO 将构建好的url添加到发送队列中
            SendUrl.getSendUrl().addUrlToQueue(url);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 构建url: http://192.168.216.111/index.html?en=e_cs&ver=1.0&pl=java_server
     * @param info
     * @return
     */
    private static String buildUrl(Map<String, String> info) {
        StringBuffer sb = new StringBuffer();
        sb.append(access_url).append("?");
        if (!info.isEmpty()){
            for (Map.Entry<String,String > en:info.entrySet()){
                if (isNotEmpty(en.getKey())) {
                    try {
                        String value = URLEncoder.encode(en.getValue(), "utf-8");
                        sb.append(en.getKey()).append("=").append(value).append("&");
                    } catch (UnsupportedEncodingException e) {
                        logger.log(Level.WARNING, "编码异常");
                    }
                }
            }
            return sb.toString().substring(0, sb.length() - 1);
        }
        return null;
    }

    /**
     * 判断参数是否为空
     * @param input
     * @return
     */
    private static boolean isEmpty(String input) {
        return  input == null || input.trim().isEmpty();
    }

    // 判断是否非空
    private static boolean isNotEmpty(String input) {
        return  !isEmpty(input);
    }

}
