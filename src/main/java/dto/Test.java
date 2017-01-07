package dto;

import org.apache.commons.codec.digest.DigestUtils;
import util.Config;

/**
 * Created by Administrator on 2016/12/29 0029.
 */
public class Test {

    public static void main(String[] args) {
        String password = DigestUtils.md5Hex(Config.get("singup.password.salt") + "111111");
        System.out.println(password);
    }

}
