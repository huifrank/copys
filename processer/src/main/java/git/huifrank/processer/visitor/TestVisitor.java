package git.huifrank.processer.visitor;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;

public class TestVisitor extends TreeTranslator {

    @Override
    public void visitMethodDef(JCTree.JCMethodDecl jcMethodDecl) {
        super.visitMethodDef(jcMethodDecl);


        System.out.println("打印一下试试"+jcMethodDecl.getName());

    }
}
