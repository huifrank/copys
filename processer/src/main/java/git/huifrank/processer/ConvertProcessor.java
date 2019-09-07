package git.huifrank.processer;


import com.google.auto.service.AutoService;
import git.huifrank.annotation.PropertieConvert;
import git.huifrank.processer.util.ProcessHelper;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import java.util.Map;
import java.util.Set;

@AutoService(PropertieConvert.class)
@SupportedAnnotationTypes("PropertieConvert")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ConvertProcessor extends AbstractProcessor {

    private Messager messager;
    private Elements elementUtils;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        elementUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(PropertieConvert.class);

        elementsAnnotatedWith.forEach(ele->{
            Map<String,AnnotationValue> annotationParam = ProcessHelper.getAnnotationParam(ele, PropertieConvert.class);

            System.out.println(annotationParam);

        });



        System.out.println("-------------process------------");

        return false;
    }

}
