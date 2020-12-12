package git.huifrank.processer.util;

import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;

import java.util.Iterator;

public class SunListUtils {


    /**
     * 在item前添加target元素
     * @param iterator 原始集合
     * @param target 目标要添加的元素
     * @param item 位置指针
     * @return
     */
    public static List prependBeforeItem(Iterator iterator,Object target,Object item){
        ListBuffer listBuffer = new ListBuffer();
        while (iterator.hasNext()){
            Object next = iterator.next();
            if(item == next){
                listBuffer.add(target);
            }
            listBuffer.add(next);
        }
        return listBuffer.toList();
    }
}
