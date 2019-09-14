package git.huifrank.processer.util;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import git.huifrank.processer.bean.Property;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertiesHelper {

    /**
     * 根据setter入参类型 和  getter出参类型 匹配方法
     * @param getter
     * @param setter
     * @return
     */
    public static List<Property> combineGetterAndSetter(List<Symbol> getter,List<Symbol> setter){

        Set<Property> get = getter.stream().map(g -> {
            Type returnType = g.type.getReturnType();
            String name = Objects.toString(g.name);
            //取方法名get/set后面的部分
            Property property = new Property(StringUtils.lowerCaseFirstLetter(name.substring(3)), returnType);
            return property;

        }).collect(Collectors.toSet());
        List<Property> set = setter.stream().map(g -> {
            Type returnType = g.type.getParameterTypes().get(0);
            String name = Objects.toString(g.name);
            //取方法名get/set后面的部分
            Property property = new Property(StringUtils.lowerCaseFirstLetter(name.substring(3)), returnType);
            return property;

        }).collect(Collectors.toList());

       return Stream.of(get, set).flatMap(Collection::stream).distinct().collect(Collectors.toList());



    }

}
