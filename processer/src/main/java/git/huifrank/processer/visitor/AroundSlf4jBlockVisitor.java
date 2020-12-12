package git.huifrank.processer.visitor;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Names;
import git.huifrank.processer.util.StatementHelper;
import git.huifrank.processer.util.SunListUtils;

import java.util.Optional;

public class AroundSlf4jBlockVisitor extends TreeTranslator {

    private TreeMaker treeMaker;
    private Names names;
    private Symbol.VarSymbol logger;

    public AroundSlf4jBlockVisitor(TreeMaker treeMaker, Names names, Symbol.VarSymbol logger){
        this.treeMaker = treeMaker;
        this.names = names;
        this.logger = logger;
    }

    @Override
    public void visitBlock(JCTree.JCBlock jcBlock) {
        super.visitBlock(jcBlock);
        StatementHelper statementHelper = new StatementHelper(treeMaker,names);
        Optional<JCTree.JCStatement> returnState = jcBlock.stats.stream().filter(jcStatement -> jcStatement.getKind() == Tree.Kind.RETURN).findFirst();

        if(returnState.isPresent()){

            JCTree.JCExpressionStatement endLogging = statementHelper.createEndLoggingStatementByReturn(logger, (JCTree.JCReturn) returnState.get());
            //在return前添加语句
            jcBlock.stats = SunListUtils.prependBeforeItem(jcBlock.stats.iterator(),endLogging,returnState.get());

        }


    }
}
