package git.huifrank.processer.visitor;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Names;
import git.huifrank.processer.util.StatementHelper;
import git.huifrank.processer.util.StringUtils;
import git.huifrank.processer.util.SunListUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;


public class AroundSlf4jMethodVisitor extends TreeTranslator {

    private TreeMaker treeMaker;
    private Names names;
    private Symbol.VarSymbol logger;

    public AroundSlf4jMethodVisitor(TreeMaker treeMaker, Names names, Symbol.VarSymbol logger){
        this.treeMaker = treeMaker;
        this.names = names;
        this.logger = logger;
    }


    @Override
    public void visitMethodDef(JCTree.JCMethodDecl jcMethodDecl) {
        super.visitMethodDef(jcMethodDecl);
        List<JCTree.JCVariableDecl> methodParam = jcMethodDecl.params;


        JCTree.JCLiteral format = treeMaker.Literal(genMethodNameLogFormat(jcMethodDecl) + genParamsLogFormat(methodParam));
        ListBuffer loggerArgs = new ListBuffer<JCTree.JCExpression>();
        loggerArgs.add(format);
        loggerArgs.addAll(convertVar2Exp(methodParam));
        JCTree.JCExpressionStatement beforeState = treeMaker.Exec(treeMaker.Apply(
                List.nil(),
                //调用方法
                treeMaker.Select(treeMaker.Ident(logger.name),names.fromString("info")),
                //入参
                loggerArgs.toList()
                )
        );
        //before
        jcMethodDecl.body.stats = jcMethodDecl.body.stats.prepend(beforeState);

        walkReturnExpression(jcMethodDecl.body.stats);

        //end
        jcMethodDecl.body.stats.stream().filter( c-> Tree.Kind.RETURN == c.getKind() ).findFirst().ifPresent( r->{
            StatementHelper statementHelper = new StatementHelper(treeMaker,names);
            JCTree.JCExpressionStatement endLogging = statementHelper.createEndLoggingStatementByReturn(logger, (JCTree.JCReturn) r);
            jcMethodDecl.body.stats = SunListUtils.prependBeforeItem(jcMethodDecl.body.stats.iterator(),endLogging,r);
        });


    }


    /**
     * 递归处理语句
     * @param statement
     */
    private void walkReturnExpression(List<JCTree.JCStatement> statement){
        for(int i = 0 ;i< statement.size();i++){
            JCTree.JCStatement jcStatement = statement.get(i);
            if(jcStatement == null){
                continue;
            }
            switch (jcStatement.getKind()){
                case BLOCK:
                    walkReturnExpression(((JCTree.JCBlock)jcStatement).stats);
                    break;

                case IF:
                    ((JCTree.JCIf)jcStatement).getThenStatement().accept(new AroundSlf4jBlockVisitor(treeMaker,names,logger));
                    JCTree.JCStatement current = ((JCTree.JCIf)jcStatement).getElseStatement();
                    walkReturnExpression(List.of(current));
                    break;
                case FOR_LOOP:
                    JCTree.JCBlock body = (JCTree.JCBlock) ((JCTree.JCForLoop) jcStatement).body;
                    walkReturnExpression(body.stats);
                    break;

                default:
                    System.out.println(jcStatement);

            }

        }

    }

    /**
     * 生成入参日志参数列表
     */
    private List<JCTree.JCExpression> convertVar2Exp(List<JCTree.JCVariableDecl> methodParam ){
        ListBuffer listBuffer = new ListBuffer();
        methodParam.stream().forEach( param -> {

            listBuffer.add(treeMaker.Ident(param));
        });
        return listBuffer.toList();
    }

    /**
     * 根据方法签名拼装生成的打印日志内容
     * @param methodDecl
     * @return
     */
    private String genMethodNameLogFormat(JCTree.JCMethodDecl methodDecl){

        return methodDecl.sym.owner.getSimpleName() + methodDecl.getName().toString();

    }

    /**
     * 根据方法入参列表生成打印日志参数内容
     * @param methodParam
     * @return
     */
    private String genParamsLogFormat(List<JCTree.JCVariableDecl> methodParam){

        StringBuilder format = new StringBuilder();
        methodParam.forEach(param -> format.append(param.getName().toString()).append(":{},"));
        return format.toString();

    }


}
