package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;


public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static Logger logger = LoggerFactory.getLogger(StringUtils.class);

    public static String isoToUtf8(String str) {
        try {
            logger.debug("要转换的字符串为：{}",str);
            return new String(str.getBytes("ISO8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("字符串{}拼写异常",str);
            throw new RuntimeException("字符串" + str +"转换异常");
        }
    }

}
