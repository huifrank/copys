package git.huifrank.processer.util;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;
import git.huifrank.processer.bean.Property;
import jdk.nashorn.internal.codegen.types.Type;

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StatementHelper {

    private TreeMaker treeMaker;
    private Names names;


    public StatementHelper(TreeMaker treeMaker, Names names) {
        this.treeMaker = treeMaker;
        this.names = names;
    }


    public JCTree.JCExpressionStatement createEndLoggingStatementByReturn(Symbol.VarSymbol logger,JCTree.JCReturn jcReturn){

        ListBuffer loggerArgs = new ListBuffer<JCTree.JCExpression>();
        loggerArgs.add(treeMaker.Literal("end {}"));
        switch (jcReturn.expr.getTag()){
            case LITERAL:
                loggerArgs.add(treeMaker.Literal(((JCTree.JCLiteral) jcReturn.expr).getValue()));
                break;
            case IDENT:
                loggerArgs.add(treeMaker.Ident(((JCTree.JCIdent) jcReturn.expr).getName()));
                break;

            default:
                System.out.println(jcReturn);
        }


        JCTree.JCExpressionStatement endLogging = treeMaker.Exec(treeMaker.Apply(
                List.nil(),
                //调用方法
                treeMaker.Select(treeMaker.Ident(logger.name),names.fromString("info")),
                //入参
                loggerArgs.toList()
                )
        );
        return endLogging;

    }


    /**
     * 创建beans赋值语句 target.setProperty(source.getProperty)
     */
    public  JCTree.JCExpressionStatement createSetPropertyStatement(Property property, JCTree.JCVariableDecl source , JCTree.JCVariableDecl target){


        //source.getXXX()
        JCTree.JCExpressionStatement getMethod = treeMaker.Exec(treeMaker.Apply(
                List.nil(),
                treeMaker.Select(treeMaker.Ident(source.sym.name)
                        ,getNameFromString("get"+StringUtils.upperCaseFirstLetter(property.getName()))
                ),
                List.nil()
                )
        );

        //target.setXXX(source.getXXX())
        JCTree.JCExpressionStatement setMethod = treeMaker.Exec(treeMaker.Apply(
                List.of(treeMaker.Type(property.getType())),
                treeMaker.Select(treeMaker.Ident(target.sym.name)
                        ,getNameFromString("set"+StringUtils.upperCaseFirstLetter(property.getName()))
                ),
                List.of(getMethod.expr)
                )
        );


        return setMethod;

    }


    public JCTree.JCExpression memberAccess(String components) {
        String[] componentArray = components.split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(getNameFromString(componentArray[0]));
        for (int i = 1; i < componentArray.length; i++) {
            expr = treeMaker.Select(expr, getNameFromString(componentArray[i]));
        }
        return expr;
    }
    public Name getNameFromString(String s) {
        return names.fromString(s);
    }

    /**
     * 取出以特定字符开头的方法
     * @param iterator
     * @param startWith
     * @return
     */
    public java.util.List<Symbol> getMethodStartWith(Iterator<Symbol> iterator, String startWith){
        Iterable<Symbol> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(),false).filter(p->{
            if(p instanceof Symbol.MethodSymbol && p.name.toString().startsWith(startWith)) {
                return true;
            }
            return false;

        }).collect(Collectors.toList());


    }
}
