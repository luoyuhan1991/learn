package org.luoyuhan.learn.java.serialiazation;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author luoyuhan
 */
@Data
@Builder
public class SerializerMain implements Serializable {
    public Integer id;
    private String name;

    public static void main(String[] args) throws IOException {
        SerializerMain serializerMain = SerializerMain.builder().id(1).name("name").build();
        Field[] fields = SerializerMain.class.getFields();
        System.out.println(Arrays.toString(fields));
        Field[] declaredFields = SerializerMain.class.getDeclaredFields();
        System.out.println(Arrays.toString(declaredFields));

        String string = JSON.toJSONString(serializerMain);
        System.out.println("原始数据是:" + string);

        InnerClass innerClass = new InnerClass(3);
        Object1 object1 = new Object1(1, "name1");
        object1.setInnerClass(innerClass);
        Object2 object2 = new Object2();
        object2.setInnerClass(innerClass);

        System.out.println(object1);
        System.out.println(object2);
        // cglib 是浅拷贝
        BeanCopier beanCopier = BeanCopier.create(Object1.class, Object2.class, false);
        beanCopier.copy(object1, object2, null);

        object2.getInnerClass().setId(4);

        System.out.println(object1);
        System.out.println(object2);

        byte[] data = ProtoStuffUtil.serializer(object1);
        System.out.println(Arrays.toString(data));
        Object1 object11 = ProtoStuffUtil.deserializer(data, Object1.class);
        System.out.println(object11);

        System.out.println("hessian, must implement serializable");
        byte[] serializer = HessianUtil.serializer(object1);
        System.out.println(Arrays.toString(serializer));
        Object1 deserializer = HessianUtil.deserializer(serializer, Object1.class);
        System.out.println(deserializer);

        Father father = new Father();
        father.setName("father");
        Son son = new Son();
        son.setName("son");

        System.out.println(father);
        Father father1 = HessianUtil.deserializer(HessianUtil.serializer(father), Father.class);
        System.out.println(father1);

        // 使用hessian序列化，子类同名属性无法反序列化
        System.out.println(son);
        Son son1 = HessianUtil.deserializer(HessianUtil.serializer(son), Son.class);
        System.out.println(son1);

        List<Father> fatherList = new ArrayList<>();
        fatherList.add(father);
        byte[] fatherBytes = HessianUtil.serializer(fatherList);
        List<Father> deserializer1 = HessianUtil.deserializer(fatherBytes, List.class);
        System.out.println(deserializer1);

        A a = new A();
        a.setA(a);
        HessianUtil.serializer(a);
        // TODO protobuf util 无法序列化循环引用
        ProtoStuffUtil.serializer(a);
    }

    @Data
    static class Father implements Serializable {
        String name;
    }

    @Data
    static class Son extends Father {
        String name;
    }

    @Data
    static class A implements Serializable {
        A a;
    }
}
