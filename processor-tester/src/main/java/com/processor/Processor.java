package com.processor;

import com.annotation.Message;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 *
 * Created by SCWANG on 2016/8/17.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"com.annotation.Message"})
public class Processor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        Elements elementUtils = processingEnv.getElementUtils();
        for (Element element : roundEnv.getElementsAnnotatedWith(Message.class)) {
            PackageElement packageElement = elementUtils.getPackageOf(element);
            messager.printMessage(Diagnostic.Kind.NOTE, "Annotation class : element = " + element);
            if (element instanceof TypeElement) {
                TypeElement classElement = (TypeElement) element;
                //获取该注解所在类的类名
                String className = classElement.getSimpleName().toString();
                //获取该注解所在类的全类名
                String fullClassName = classElement.getQualifiedName().toString();
                messager.printMessage(Diagnostic.Kind.NOTE,
                        "Annotation class : className = " + className);
                messager.printMessage(Diagnostic.Kind.NOTE,
                        "Annotation class : fullClassName = " + fullClassName);
            }
            Element packageElement = element.getEnclosingElement();
            if (packageElement instanceof PackageElement) {
                //获取该注解所在类的包名
                String packageName = ((PackageElement) packageElement).getQualifiedName().toString();
                messager.printMessage(Diagnostic.Kind.NOTE,
                        "Annotation class : packageName = " + packageName);
            }
            Element variableElement = element.getEnclosingElement();
            if (variableElement instanceof VariableElement) {
                //获取方法名
                String methodName = variableElement.getSimpleName().toString();
                //获取该注解的值
                int id = variableElement.getAnnotation(Message.class).value();
                messager.printMessage(Diagnostic.Kind.NOTE,
                        "Annotation class : methodName = " + methodName);
                messager.printMessage(Diagnostic.Kind.NOTE,
                        "Annotation class : id = " + id);
            }
        }
        return true;
    }
}
