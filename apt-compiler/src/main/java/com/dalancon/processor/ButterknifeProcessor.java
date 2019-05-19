package com.dalancon.processor;

import com.dalancon.annotations.BindView;
import com.dalancon.annotations.OnClick;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.ElementFilter;

/**
 * Created by dalancon on 2019/5/19.
 */

public class ButterknifeProcessor {

    public static void process(RoundEnvironment roundEnvironment, MyProcessor abstractProcessor) {
        //Map activity -> onclick
//        Map<String, Set<Element>> elementsMap = new HashMap<>();
//        Set<? extends Element> elements = ElementFilter.methodsIn(roundEnvironment.getElementsAnnotatedWith(OnClick.class));
//
//        System.out.println("elements -->" + elements);
//
//        for (Element element : elements) {
//            Set<Element> fields = elementsMap.get(element.getEnclosingElement().toString());
//            if (fields == null) {
//                fields = new HashSet<>();
//                elementsMap.put(element.getEnclosingElement().toString(), fields);
//            }
//            fields.add(element);
//        }
//
//
//        for (Map.Entry<String, Set<Element>> entrySet : elementsMap.entrySet()) {
//            String activityName = entrySet.getKey();
//            Set<Element> methodElements = entrySet.getValue();
//            for (Element methodElement : methodElements) {
//
//                if (methodElement instanceof ExecutableElement) {
//                    ExecutableElement executableElement = (ExecutableElement) methodElement;
//                    int value = executableElement.getAnnotation(OnClick.class).value();
//
//
//
//
//                }
//            }
//
//        }


        bindViewProcess(roundEnvironment, abstractProcessor);

    }

    private static void bindViewProcess(RoundEnvironment roundEnvironment, MyProcessor abstractProcessor) {
        //Map activity -> bindviews
        Map<String, Set<Element>> elementsMap = new HashMap<>();

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);

        for (Element element : elements) {
            System.out.println("getEnclosingElement -->" + element.getEnclosingElement());
            System.out.println("getSimpleName -->" + element.getSimpleName());
//            System.out.println("getEnclosedElements -->" + element.getEnclosedElements());
//            System.out.println("element -->" + element);
            Set<Element> fields = elementsMap.get(element.getEnclosingElement().toString());
            if (fields == null) {
                fields = new HashSet<>();
                elementsMap.put(element.getEnclosingElement().toString(), fields);
            }
            fields.add(element);
        }
        try {
            for (Map.Entry<String, Set<Element>> entry : elementsMap.entrySet()) {
                String activityName = entry.getKey();
                Set<Element> filedSet = entry.getValue();

                ClassName activityClassName = ClassName.bestGuess(activityName);

                String simpleName = activityClassName.simpleName();
                System.out.println("---------->" + simpleName);
                MethodSpec.Builder bindMethodBuilder = MethodSpec.methodBuilder("bind")
                        .addJavadoc("此方法由apt自动生成，请勿修改")
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(activityClassName, "target");
                ClassName utilsClassName = ClassName.bestGuess("com.dalancon.aptdemo.utils.Utils");
                for (Element filed : filedSet) {
                    bindMethodBuilder.addCode("target.$N = $T.findViewById(target,$L);\n",
                            filed.getSimpleName(), utilsClassName,
                            filed.getAnnotation(BindView.class).value());
                }

                TypeSpec typeSpec = TypeSpec.classBuilder(simpleName + "_ViewBinding")
                        .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                        .addMethod(bindMethodBuilder.build())
                        .build();


                String packageName = activityClassName.packageName();
                JavaFile.builder(packageName, typeSpec)
                        .addFileComment("此文件由apt自动生成，请勿修改")
                        .build()
                        .writeTo(abstractProcessor.mFiler);


            }
        } catch (Exception e) {
            System.out.println("error---------->");
            e.printStackTrace();
        }
    }

}
