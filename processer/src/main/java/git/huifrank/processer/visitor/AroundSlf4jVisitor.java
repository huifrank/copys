package git.huifrank.processer.visitor;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;
import git.huifrank.processer.util.StringUtils;

import java.util.stream.Collectors;


public class AroundSlf4jVisitor extends TreeTranslator {

    private TreeMaker treeMaker;
    private Names names;

    public AroundSlf4jVisitor(TreeMaker treeMaker, Names names ){
        this.treeMaker = treeMaker;
        this.names = names;
    }


    @Override
    public void visitMethodDef(JCTree.JCMethodDecl jcMethodDecl) {
        super.visitMethodDef(jcMethodDecl);
        List<JCTree.JCVariableDecl> methodParam = jcMethodDecl.params;

        JCTree.JCVariableDecl target = methodParam.get(0);
        JCTree.JCExpressionStatement logState = treeMaker.Exec(treeMaker.Apply(
                List.nil(),
                treeMaker.Select(treeMaker.Ident(target.sym.name)
                        ,names.fromString("toString"))
                ,
                List.nil()
                )
        );


        jcMethodDecl.body.stats = jcMethodDecl.body.stats.append((logState));



    }
}
