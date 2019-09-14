package git.huifrank.processer.visitor;

import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Scope;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;
import com.sun.tools.javac.util.StringUtils;
import git.huifrank.annotation.PropertiesConvert;
import git.huifrank.processer.bean.Property;
import git.huifrank.processer.util.ProcessHelper;
import git.huifrank.processer.util.PropertiesHelper;

import javax.lang.model.element.AnnotationValue;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class GeneratePropertiesCopyVisitor extends TreeTranslator {


    private TreeMaker treeMaker;
    private Names names;
    private Map<String, AnnotationValue> annotationValueMap;

    public GeneratePropertiesCopyVisitor(TreeMaker treeMaker, Names names, Map<String, AnnotationValue> annotationValueMap){
        this.treeMaker = treeMaker;
        this.names = names;
        this.annotationValueMap = annotationValueMap;
    }


    @Override
    public void visitMethodDef(JCTree.JCMethodDecl jcMethodDecl) {
        super.visitMethodDef(jcMethodDecl);

        System.out.println("打印一下试试"+jcMethodDecl.getName());


        //方法入参
        List<JCTree.JCVariableDecl> methodParam = jcMethodDecl.params;
        AnnotationValue from = annotationValueMap.get("source()");
        AnnotationValue to = annotationValueMap.get("target()");


        Optional<JCTree.JCVariableDecl> source = selectTargetParam(methodParam, from);
        Optional<JCTree.JCVariableDecl> target = selectTargetParam(methodParam, to);
        //得到成员迭代器
        Iterator<Symbol> sourceIte = source.get().sym.type.tsym.members().getElements().iterator();
        Iterator<Symbol> targetIte = target.get().sym.type.tsym.members().getElements().iterator();

        //遍历入参类中所有成员  取出setters/getters
        java.util.List<Symbol> setter = getMethodStartWith(targetIte, "set");
        java.util.List<Symbol> getter = getMethodStartWith(sourceIte, "get");

        java.util.List<Property> properties = PropertiesHelper.combineGetterAndSetter(getter, setter);

        JCTree.JCExpressionStatement statement = treeMaker.Exec(treeMaker.Apply(
                List.of(memberAccess("java.lang.String")),//参数类型
                memberAccess("java.lang.System.out.println"),
                List.of(treeMaker.Literal("aaa"))//参数集合
                )
        );


        //写入方法
        jcMethodDecl.body.stats = jcMethodDecl.body.stats.append(statement);

    }


    /**
     * 取出以特定字符开头的方法
     * @param iterator
     * @param startWith
     * @return
     */
    private java.util.List<Symbol> getMethodStartWith(Iterator<Symbol> iterator, String startWith){
        Iterable<Symbol> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(),false).filter( p->{
            if(p instanceof Symbol.MethodSymbol && p.name.toString().startsWith(startWith)) {
                return true;
            }
            return false;

        }).collect(Collectors.toList());


    }


    /**
     * 根据注解中传入的类型取对应的方法入参
     * @param param
     * @param target
     * @return
     */
    private Optional<JCTree.JCVariableDecl> selectTargetParam(List<JCTree.JCVariableDecl> param, AnnotationValue target ){
        return param.stream().filter(decl ->{
            return Objects.equals(decl.sym.type, ((Attribute.Class) target).getValue());

        }).findFirst();
    }


    private JCTree.JCExpression memberAccess(String components) {
        String[] componentArray = components.split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(getNameFromString(componentArray[0]));
        for (int i = 1; i < componentArray.length; i++) {
            expr = treeMaker.Select(expr, getNameFromString(componentArray[i]));
        }
        return expr;
    }
    private Name getNameFromString(String s) {
        return names.fromString(s);
    }







}
