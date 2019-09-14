package git.huifrank.processer.util;

public class StringUtils {

    /**
     * 将首字母转小写
     * @param str
     * @return
     */
    public static String lowerCaseFirstLetter(String str){
        if("".equals(str) || null == str){
            return str;
        }

        return str.substring(0,1).toLowerCase()+str.substring(1);

    }

    /**
     * 将首字母转大写
     * @param str
     * @return
     */
    public static String upperCaseFirstLetter(String str){
        if("".equals(str) || null == str){
            return str;
        }

        return str.substring(0,1).toUpperCase()+str.substring(1);

    }
}
