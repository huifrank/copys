package git.huifrank.processer.util;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProcessHelper {


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
}
