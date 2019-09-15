package git.huifrank.processer.util;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import git.huifrank.processer.bean.Property;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    public static Iterator<Symbol> getSymbolsIncludeParent(Optional<JCTree.JCVariableDecl> variableDecl){

        List<Iterator<Symbol>> superMembers = getSuperSymbols((Type.ClassType) ((Type.ClassType) variableDecl.get().sym.type).supertype_field);

        Iterator<Symbol> myMembers = variableDecl.get().sym.type.tsym.members().getElements().iterator();

        //合并结果
        superMembers.add(myMembers);
        return superMembers.stream().flatMap(i -> {
            Iterable<Symbol> iterable = () -> i;
            return StreamSupport.stream(iterable.spliterator(), false);
        }).collect(Collectors.toList()).iterator();

    }

    private static List<Iterator<Symbol>> getSuperSymbols(Type.ClassType classType){
        if("java.lang.Object".equals(classType.tsym.type.toString())    ||
           "none".equals(Objects.toString(classType.supertype_field)) ) {
            //如果没有父类  或 父类是Object时  (Object没有父类)
            return Collections.EMPTY_LIST;
        }
        List<Iterator<Symbol>> res = new ArrayList<>();


        Iterator<Symbol> elements = classType.tsym.members().getElements().iterator();
        List<Iterator<Symbol>> superSymbols = getSuperSymbols((Type.ClassType) classType.supertype_field);
        if(elements.hasNext()){
            res.add(elements);
        }
        res.addAll(superSymbols);
        return res;
    }






}
