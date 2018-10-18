package com.unicorn.utils;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HtmlRegexpUtils {

    private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签

    private final static String regxpForSpecial = "\\&[a-zA-Z]{1,10};";


    private final static String regxpForImgTag = "<\\s*img\\s+([^>]*)\\s*>"; // 找出IMG标签

    private final static String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\""; // 找出IMG标签的SRC属性

    public HtmlRegexpUtils() {
    }

    public static String filterHtml(String htmStr) {
        if (StringUtils.isEmpty(htmStr)) {
            return "";
        }
        Pattern htmlPattern = Pattern.compile(regxpForHtml);
        Pattern specialPattern = Pattern.compile(regxpForSpecial, Pattern.CASE_INSENSITIVE);
        Matcher matcher = htmlPattern.matcher(htmStr);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return specialPattern.matcher(sb.toString()).replaceAll("");
    }

    public static List<String> getImageSrc(String htmlStr) {

        String img = "";
        Pattern p_image;
        Matcher m_image;
        List<String> pics = new ArrayList();
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

}
