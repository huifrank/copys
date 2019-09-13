package git.huifrank.processer.visitor;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

public class TestVisitor extends TreeTranslator {


    private TreeMaker treeMaker;
    private Names names;

    public TestVisitor(TreeMaker treeMaker, Names names){
        this.treeMaker = treeMaker;
        this.names = names;
    }


    @Override
    public void visitMethodDef(JCTree.JCMethodDecl jcMethodDecl) {
        super.visitMethodDef(jcMethodDecl);

        System.out.println("打印一下试试"+jcMethodDecl.getName());


        JCTree.JCExpressionStatement statement = treeMaker.Exec(treeMaker.Apply(
                List.of(memberAccess("java.lang.String")),//参数类型
                memberAccess("java.lang.System.out.println"),
                List.of(treeMaker.Literal("aaa"))//参数集合
                )
        );
        jcMethodDecl.body.stats = jcMethodDecl.body.stats.append(statement);

    }


    private JCTree.JCExpression memberAccess(String components) {
        String[] componentArray = components.split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(getNameFromString(componentArray[0]));
        for (int i = 1; i < componentArray.length; i++) {
            expr = treeMaker.Select(expr, getNameFromString(componentArray[i]));
        }
        return expr;
    }
    private Name getNameFromString(String s) { return names.fromString(s); }







}
