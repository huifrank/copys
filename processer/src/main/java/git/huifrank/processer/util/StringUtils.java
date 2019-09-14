package git.huifrank.processer.util;

public class StringUtils {

    /**
     * 将首字母转小写
     * @param str
     * @return
     */
    public static String lowercaseFirstLetter(String str){
        if("".equals(str) || null == str){
            return str;
        }

        return str.substring(0,1).toLowerCase()+str.substring(1);

    }
}
