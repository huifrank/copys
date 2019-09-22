package git.huifrank.processer.util;


import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProcessHelper {


    /**
     * 获取到指定元素上所标识注解的参数
     * @param element
     * @param targetAnnotation
     * @return  key -> value
     */
    public static Map<String, AnnotationValue> getAnnotationParam(Element element, Class targetAnnotation){

        Map<String ,AnnotationValue> res = new HashMap<>();



        Optional<? extends AnnotationMirror> mirror = element.getAnnotationMirrors().stream()
                .filter(c -> c.getAnnotationType().toString().equals(targetAnnotation.getName())).findFirst();


        mirror.ifPresent(mir->mir.getElementValues().entrySet().stream().forEach( c-> {

                    res.put(c.getKey().toString(),c.getValue());

                }
        ));

        return res;

    }

    /**
     * todo
     * @param jcClassDecl
     * @param methodName
     * @param paramTypes
     * @param resType
     * @return
     */
    public static boolean hasMethod(JCTree.JCClassDecl jcClassDecl, String methodName, java.util.List<String> paramTypes, String resType){
        return true;
    }


    /**
     * 获取方法参数名称 &类型
     * @param element
     * @return name -> type
     */
    public static Map<String,Type> getMethodParamNames(Element element){
        List<Symbol.VarSymbol> params = ((Symbol.MethodSymbol) element).params();

       return params.stream().collect(Collectors.toMap(p -> p.name.toString(), p -> p.type));

    }

}
