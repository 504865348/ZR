package com.joshua.zhangrui.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ============================================================
 * <p/>
 * 版 权 ： 江苏明杰软件科技有限公司  (c) 2016
 * <p/>
 * 作 者 : 吴奇俊
 * <p/>
 * 版 本 ： 1.0
 * <p/>
 * 创建日期 ： 2016/4/9 15:15
 * <p/>
 * 描 述 ：正则表达式判断工具
 * <p/>
 * ============================================================
 **/
public class JudgeUtils {
    /**
     * 判断是否是手机号码
     * @param mobiles 手机号码
     * @return true/false
     */
    public static boolean isMobileNO(String mobiles){

     Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

      Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    /**
     * 判断服务器返回值
     * @param flag 服务器返回值 格式：方法名#success/fail
     * @return boolean
     */
    public static boolean isTrue(String flag){
        String success[]=flag.split("#");
        return success[1].equals("success");
    }

    /**
     * 判断服务器返回值
     * @param flag 服务器返回值 格式：方法名#success/fail
     * @return boolean
     */
    public static boolean isFalse(String flag){
        String success[]=flag.split("#");
        return success[1].equals("fail");
    }


    /**
     * 判断服务器返回天数
     * 是否有资格进入训练
     * @param flag 服务器返回值 格式：方法名#success/fail#天数
     * @return String
     */
    public static String returnDay(String flag){
        String success[]=flag.split("#");
        return success[2];
    }
    /**
     * 判断今日天数
     * 是否满14天要开启升级问答
     * @param flag 服务器返回值 格式：方法名#success/fail#success/fail
     * @return String
     */
    public static boolean returnLevelUp(String flag){
        String success[]=flag.split("#");
        return success[3].equals("success");
    }
    /**
     * 判断今日天数
     * 是否要刷新refreshTime
     * @param flag 服务器返回值 格式：方法名#success/fail#success/fail
     * @return String
     */
    public static String returnTime(String flag){
        String success[]=flag.split("#");
        return success[2];
    }

    /**
     * 判断服务器返回值
     * @param result 服务器返回值 格式：json#int
     * @return String json数据
     */
    public static String getResult(String result){
        String json[]= result.split("#");
        return json[1];
    }

    /**
     * 判断服务器返回值
     * @param result 服务器返回值 格式：json#int
     * @return String 百分比证 整数
     */
    public static String getStatu(String result){
        String json[]= result.split("#");
        return json[2];
    }
}
