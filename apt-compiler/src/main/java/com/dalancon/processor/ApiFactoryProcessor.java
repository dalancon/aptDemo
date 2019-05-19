package com.dalancon.processor;

import com.dalancon.annotations.ApiFactory;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;

/**
 * Created by dalancon on 2019/5/19.
 */

public class ApiFactoryProcessor {

    public static void process(RoundEnvironment roundEnvironment, MyProcessor abstractProcessor) {
        Set<TypeElement> typeElements = ElementFilter.typesIn(roundEnvironment.getElementsAnnotatedWith(ApiFactory.class));

        for (TypeElement element : typeElements) {
            System.out.println("element----->" + element.getEnclosingElement());

            /**
             *
             getEnclosedElements----->login(java.lang.String,java.util.Map<java.lang.String,java.lang.String>)
             getDefaultValue----->null
             getParameters----->url,params
             getReturnType----->java.lang.String
             getSimpleName----->login
             getThrownTypes----->
             getTypeParameters----->
             getModifiers----->[public, abstract]
             */

            TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder("ApiFactory")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

            List<? extends Element> enclosedElements = element.getEnclosedElements();
            for (Element enclosedElement : enclosedElements) {
                //获取element下包含的方法  方法对应的是：ExecutableElement  属性对应的是：VariableElement
                if (enclosedElement instanceof ExecutableElement) {
                    System.out.println("getEnclosedElements----->" + enclosedElement);
                    ExecutableElement executableElement = (ExecutableElement) enclosedElement;

                    System.out.println("getDefaultValue----->" + executableElement.getDefaultValue());
                    System.out.println("getParameters----->" + executableElement.getParameters().get(0));
                    System.out.println("getReturnType----->" + executableElement.getReturnType());
                    System.out.println("getSimpleName----->" + executableElement.getSimpleName());
                    System.out.println("getThrownTypes----->" + executableElement.getThrownTypes());
                    System.out.println("getTypeParameters----->" + executableElement.getTypeParameters());
                    System.out.println("getModifiers----->" + executableElement.getModifiers());
                    System.out.println("======================================");

                    MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder(executableElement.getSimpleName().toString())
                            .addJavadoc("此代码由apt自动生成")
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC);
                    methodSpecBuilder.returns(TypeName.get(executableElement.getReturnType()));
                    List<? extends VariableElement> parameters = executableElement.getParameters();
                    List<String> paramsName = new ArrayList<>();
                    for (VariableElement param : parameters) {
                        methodSpecBuilder.addParameter(TypeName.get(param.asType()), param.getSimpleName().toString());
                        paramsName.add(param.getSimpleName().toString());
                    }

                    StringBuilder sb = new StringBuilder();
                    sb.append("return Api.getInstance().service.").append(executableElement.getSimpleName().toString())
                            .append("(");
                    for (String s : paramsName) {
                        sb.append(s).append(",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append(");\n");

                    methodSpecBuilder.addCode(sb.toString());
                    typeSpecBuilder.addMethod(methodSpecBuilder.build()).build();
                }
            }
            try {
                JavaFile.builder(element.getEnclosingElement().toString(), typeSpecBuilder.build()).build().writeTo(abstractProcessor.mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
