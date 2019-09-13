package git.huifrank.processer;


import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;
import git.huifrank.annotation.PropertiesConvert;
import git.huifrank.processer.util.ProcessHelper;
import git.huifrank.processer.visitor.TestVisitor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import java.util.Map;
import java.util.Set;

@AutoService(PropertiesConvert.class)
@SupportedAnnotationTypes("PropertiesConvert")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ConvertProcessor extends AbstractProcessor {

    private Messager messager;
    private Elements elementUtils;
    private Filer filer;
    private Trees trees;
    private TreeMaker treeMaker;
    private Names names;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        elementUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
        trees = Trees.instance(processingEnv);
        Context context =( (JavacProcessingEnvironment) processingEnvironment).getContext();
        treeMaker = TreeMaker.instance((context));
        this.names = Names.instance(context);



    }
    @Override
    public SourceVersion getSupportedSourceVersion() {
        if (SourceVersion.latest().compareTo(SourceVersion.RELEASE_8) > 0) {
            return SourceVersion.latest();
        } else {
            return SourceVersion.RELEASE_8;
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(PropertiesConvert.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(PropertiesConvert.class);

        elementsAnnotatedWith.forEach(ele->{
            Map<String,AnnotationValue> annotationParam = ProcessHelper.getAnnotationParam(ele, PropertiesConvert.class);
            Map<String, Type> methodParamNames = ProcessHelper.getMethodParamNames(ele);

            JCTree tree = (JCTree) trees.getTree( ele );
            tree.accept(new TestVisitor(treeMaker,names));


            System.out.println(annotationParam);

        });



        System.out.println("-------------process------------");

        return false;
    }

}
