package git.huifrank.processer.visitor;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Names;
import git.huifrank.processer.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;


public class AroundSlf4jVisitor extends TreeTranslator {

    private TreeMaker treeMaker;
    private Names names;
    private Symbol.VarSymbol logger;

    public AroundSlf4jVisitor(TreeMaker treeMaker, Names names, Symbol.VarSymbol logger){
        this.treeMaker = treeMaker;
        this.names = names;
        this.logger = logger;
    }


    @Override
    public void visitMethodDef(JCTree.JCMethodDecl jcMethodDecl) {
        super.visitMethodDef(jcMethodDecl);
        List<JCTree.JCVariableDecl> methodParam = jcMethodDecl.params;


        JCTree.JCLiteral format = treeMaker.Literal((genParamsLogFormat(methodParam)));
        ListBuffer loggerArgs = new ListBuffer<>();
        loggerArgs.add(format);
        loggerArgs.addAll(convertVar2Exp(methodParam));
        JCTree.JCExpressionStatement logState = treeMaker.Exec(treeMaker.Apply(
                List.nil(),
                //调用方法
                treeMaker.Select(treeMaker.Ident(logger.name),names.fromString("info")),
                //入参
                loggerArgs.toList()
                )
        );

        //before
        jcMethodDecl.body.stats = jcMethodDecl.body.stats.prepend(logState);

    }
    private List convertVar2Exp(List<JCTree.JCVariableDecl> methodParam ){
        ListBuffer listBuffer = new ListBuffer();
        methodParam.stream().forEach( param -> {

            listBuffer.add(treeMaker.Ident(param));
        });
        return listBuffer.toList();
    }

    /**
     * 对入参参数生成toString调用
     * @param methodParam
     * @return
     */
    private List genParamsInvokeToString(List<JCTree.JCVariableDecl> methodParam){
        ListBuffer listBuffer = new ListBuffer();
        methodParam.forEach(param ->{
            JCTree.JCMethodInvocation toString = treeMaker.Apply(
                    List.nil(),
                    treeMaker.Select(treeMaker.Ident(param.name), names.fromString("toString")),
                    List.nil()
            );
            listBuffer.add(toString);

        });
        return listBuffer.toList();
    }
    private String genParamsLogFormat(List<JCTree.JCVariableDecl> methodParam){

        StringBuilder format = new StringBuilder();
        methodParam.forEach(param -> format.append(param.getName().toString()).append(":{},"));
        return format.toString();

    }


}
