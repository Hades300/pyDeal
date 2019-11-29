package com.galaxyzeta;
import com.galaxyzeta.beans.User;

import java.lang.annotation.*;
import java.lang.reflect.*;

public class JavaPlayground {
    public static void main(String[] args){
        User user = new User();
        Class userClass = user.getClass();
        Method[] methods = userClass.getDeclaredMethods();
        System.out.println(userClass);
        for(Method m: methods){
            System.out.printf("RetType=%s, MtdName=%s\n",m.getReturnType(),m.getName());
        }
        try {
            Method specificMethod = userClass.getMethod("setPassword", String.class);
            specificMethod.invoke(user, "Galaxyzeta's password");
            System.out.println(user.toString());
        } catch(Exception e){
            e.printStackTrace();
        }
        AnnotationTester annotationTester = new AnnotationTester();
        Class annotationClass = AnnotationTester.class;
        Method[] allMethod = annotationClass.getDeclaredMethods();
        for(Method m : allMethod){
            Annotation[] annotations = m.getDeclaredAnnotations();
            for(Annotation a : annotations){
                System.out.println(a.toString());
                if(a.annotationType() == GetReturnValueTarget.class){
                    try {
                        m.setAccessible(true);
                        m.invoke(annotationTester);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class AnnotationTester{
    @GetReturnValueTarget(id = 1)
    private void Test(){
        System.out.println("You can even invoke private method !?");
    }
}

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface GetReturnValueTarget{
    int id();
}