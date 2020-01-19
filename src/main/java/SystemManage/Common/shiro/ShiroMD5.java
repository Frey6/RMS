package SystemManage.Common.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;

public class ShiroMD5 {

    /*number表示用户账号，代表加密盐值，加密old字符串，返回加密后的数据*/
    public static String GetPwd(String username,String password)
    {
        // 密码加盐
        Md5Hash md5 = new Md5Hash(password, username, 1024);
        String newMd5Password = md5.toHex();
       //  System.out.println("加盐后的密码是:" +newMd5Password);
        // 返回新密码
        return newMd5Password;
    }
}
