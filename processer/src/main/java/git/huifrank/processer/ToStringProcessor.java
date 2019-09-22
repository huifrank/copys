package git.huifrank.processer;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.sun.source.util.Trees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;
import git.huifrank.annotation.PropertiesConvert;
import git.huifrank.annotation.ToString;
import git.huifrank.processer.util.ProcessHelper;
import git.huifrank.processer.visitor.CreateToStringMethodVisitor;
import git.huifrank.processer.visitor.GeneratePropertiesCopyVisitor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.Map;
import java.util.Set;

@AutoService(ToString.class)
@SupportedAnnotationTypes("ToString")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ToStringProcessor  extends AbstractProcessor {

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
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(ToString.class);

        //遍历被当前注解修饰的元素
        elementsAnnotatedWith.forEach(ele->{
            Map<String, AnnotationValue> annotationParam = ProcessHelper.getAnnotationParam(ele, ToString.class);

            JCTree tree = (JCTree) trees.getTree( ele );
            tree.accept(new CreateToStringMethodVisitor(treeMaker,names,annotationParam));


            System.out.println(annotationParam);

        });


        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(ToString.class.getCanonicalName());
    }
}
