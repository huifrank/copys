package git.huifrank.processer.util;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;
import git.huifrank.processer.bean.Property;

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

    public  JCTree.JCExpressionStatement createSetPropertyStatement(Property property, JCTree.JCVariableDecl source , JCTree.JCVariableDecl target){

        String paramType = property.getType().toString();

        JCTree.JCExpressionStatement setMethod = treeMaker.Exec(treeMaker.Apply(
                List.of(memberAccess(paramType)),//参数类型
                memberAccess("java.lang.System.out.println"),
                List.of(treeMaker.Ident(source))
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
