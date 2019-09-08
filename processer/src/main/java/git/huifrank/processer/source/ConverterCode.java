package git.huifrank.processer.source;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import java.lang.reflect.Method;

public class ConverterCode {

    public void propertiesCode(Class source,Class target){

        Method[] sourceMethods = source.getMethods();

        MethodSpec methodSpec = MethodSpec.methodBuilder("convert"+source.getName()+"2"+target.getName()).build();
    }
}
