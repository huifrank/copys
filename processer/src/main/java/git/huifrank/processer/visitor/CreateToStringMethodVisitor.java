package git.huifrank.processer.visitor;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Names;
import git.huifrank.processer.util.ProcessHelper;
import git.huifrank.processer.util.StatementHelper;

import javax.lang.model.element.AnnotationValue;
import java.util.Collections;
import java.util.Map;

public class CreateToStringMethodVisitor extends TreeTranslator {

    private TreeMaker treeMaker;
    private Names names;
    private Map<String, AnnotationValue> annotationValueMap;

    public CreateToStringMethodVisitor(TreeMaker treeMaker, Names names, Map<String, AnnotationValue> annotationValueMap){
        this.treeMaker = treeMaker;
        this.names = names;
        this.annotationValueMap = annotationValueMap;
    }


    @Override
    public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
        super.visitClassDef(jcClassDecl);
        StatementHelper statementHelper = new StatementHelper(treeMaker,names);
        boolean hasToString = ProcessHelper.hasMethod(jcClassDecl, "toString", Collections.EMPTY_LIST, "java.lang.String");
        if(hasToString){
            return;
        }

    }
}
