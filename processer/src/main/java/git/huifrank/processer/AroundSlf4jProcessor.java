package git.huifrank.processer;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.sun.source.tree.Tree;
import com.sun.source.util.Trees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;
import git.huifrank.annotation.AroundSlf4j;
import git.huifrank.processer.visitor.AroundSlf4jVisitor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.Set;

@AutoService(AroundSlf4j.class)
@SupportedAnnotationTypes("AroundSlf4j")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AroundSlf4jProcessor extends AbstractProcessor {

    private Elements elements;
    private Filer filer;
    private Trees trees;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elements = processingEnv.getElementUtils();
        this.filer = processingEnv.getFiler();
        trees = Trees.instance(processingEnv);
        Context context =( (JavacProcessingEnvironment) processingEnv).getContext();
        treeMaker = TreeMaker.instance((context));
        this.names = Names.instance(context);

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(AroundSlf4j.class);
        elementsAnnotatedWith.forEach(ele->{

            if(ele.getKind() == ElementKind.METHOD){

                JCTree tree = (JCTree) trees.getTree(ele);
                tree.accept(new AroundSlf4jVisitor(treeMaker,names));


            }

        });


            return false;
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(AroundSlf4j.class.getCanonicalName());
    }
}
