package com.ldx;

/**
 * @ClassName TestSend
 * @Description 测试
 * @Author Lidexiu
 * @Date 2018/8/15 11:22
 * @Version 1.0
 **/
public class TestSend {
    public static void main(String[] args) {
        System.out.println(new LogEngineLSdk().Charge("123460", "aaa-dd", "cs"));
    }
}
