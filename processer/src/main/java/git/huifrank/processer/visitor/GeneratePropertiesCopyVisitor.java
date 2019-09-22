package git.huifrank.processer.visitor;

import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;
import git.huifrank.processer.bean.Property;
import git.huifrank.processer.util.PropertiesHelper;
import git.huifrank.processer.util.StatementHelper;

import javax.lang.model.element.AnnotationValue;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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


        StatementHelper statementHelper = new StatementHelper(treeMaker,names);


        //方法入参
        List<JCTree.JCVariableDecl> methodParam = jcMethodDecl.params;
        AnnotationValue from = annotationValueMap.get("source()");
        AnnotationValue to = annotationValueMap.get("target()");


        Optional<JCTree.JCVariableDecl> source = selectTargetParam(methodParam, from);
        Optional<JCTree.JCVariableDecl> target = selectTargetParam(methodParam, to);

        //得到成员迭代器
        Iterator<Symbol> sourceIte = PropertiesHelper.getSymbolsIncludeParent(source);
        Iterator<Symbol> targetIte = PropertiesHelper.getSymbolsIncludeParent(target);

        //遍历入参类中所有成员  取出setters/getters
        java.util.List<Symbol> setter = statementHelper.getMethodStartWith(targetIte, "set");
        java.util.List<Symbol> getter = statementHelper.getMethodStartWith(sourceIte, "get");

        java.util.List<Property> properties = PropertiesHelper.combineGetterAndSetter(getter, setter);

        //生成代码
        java.util.List<JCTree.JCExpressionStatement> statements = properties.stream().map(p -> {
            return statementHelper.createSetPropertyStatement(p, source.get(), target.get());
        }).collect(Collectors.toList());




        //写入方法
        jcMethodDecl.body.stats = jcMethodDecl.body.stats.appendList(List.from(statements));

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









}
