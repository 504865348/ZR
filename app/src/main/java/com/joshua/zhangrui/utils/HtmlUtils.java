package com.joshua.zhangrui.utils;

/**
 * ============================================================
 * <p>
 * 版 权 ： 吴奇俊  (c) 2016
 * <p>
 * 作 者 : 吴奇俊
 * <p>
 * 版 本 ： 1.0
 * <p>
 * 创建日期 ： 2016/11/18 17:54
 * <p>
 * 描 述 ：与html相关的工具类
 * <p>
 * ============================================================
 **/

public class HtmlUtils {

    /**
     * 删除html中的标签
     * @param content Html文本
     * @return 纯文本
     */
    public static String stripHtml(String content) {
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }
}
