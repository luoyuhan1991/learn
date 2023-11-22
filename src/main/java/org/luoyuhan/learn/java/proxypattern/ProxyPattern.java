package org.luoyuhan.learn.java.proxypattern;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author luoyuhan
 */
public class ProxyPattern implements InvocationHandler, MethodInterceptor, MethodHandler {
    public static void main(String[] args) throws Exception {
        ProxyPattern proxyPattern = new ProxyPattern();
        // jdk proxy
        SayInterface sayService = new SayHelloService();
        SayInterface proxiedTarget = (SayInterface) proxyPattern.createReflectProxy(sayService);
        proxiedTarget.say();

        // cglib proxy
        SayHelloClass sayHelloClass = new SayHelloClass();
        SayHelloClass cGlibProxiedClass = (SayHelloClass) proxyPattern.createCglibProxy(sayHelloClass);
        cGlibProxiedClass.say();

        // java assist
        SayInterface sayInterface = createJavassistBytecodeDynamicProxy();
        System.out.println("java assist 字节码 前");
        sayInterface.say();
        System.out.println("java assist 字节码 后");

        SayInterface sayInterfaceFromJavaAssist = (SayInterface) proxyPattern.getProxyFromJavaAssist(sayService);
        sayInterfaceFromJavaAssist.say();
    }

    // 使用jdk反射进行代理开始*****, 代理类实现InvocationHandler接口，使用被代理对象的方法，method.invoke(targetClass, args);
    private SayInterface targetClass;

    public Object createReflectProxy(SayInterface targetClass) {
        //传入真实实现类, 本身要做的事情会由他自己做, 代理类会额外进行其他增强操作
        this.targetClass = targetClass;
        //获取本类类加载器
        ClassLoader classLoader = ProxyPattern.class.getClassLoader();
        ///获取被代理对象的所有接口
        Class[] clazz = targetClass.getClass().getInterfaces();
        //生成代理类并返回，参数为代理类的类加载器，被代理类的接口，和代理类
        return Proxy.newProxyInstance(classLoader, clazz, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("JDKProxy前置增强");
        Object obj = method.invoke(targetClass, args);
        System.out.println("JDKProxy后置增强");
        return obj;
    }
    //使用jdk反射进行代理结束*****

    //使用cglib进行代理开始*****, 实现MethodInterceptor
    public Object createCglibProxy(SayHelloClass targetClass) {
        //创建一个动态类对象
        Enhancer enhancer = new Enhancer();
        //确定要增强的类,即设置父类为被代理类
        enhancer.setSuperclass(targetClass.getClass());
        //添加回调函数
        enhancer.setCallback(this);
        //返回创建的代理类
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Cglib前置增强");
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("Cglib后置增强");
        return object;
    }
    // 使用cglib进行代理结束*****

    // java assist
    // 通过 method Handler进行invoke
    @Override
    public Object invoke(Object obj, Method method, Method process, Object[] objects) throws Throwable {
        Object result;
        System.out.println("javaassist MethodHandler 前");
        result = process.invoke(obj, objects);
        System.out.println("javaassist MethodHandler 后");
        return result;
    }

    // 获取代理
    public Object getProxyFromJavaAssist(SayInterface sayInterface) throws IllegalAccessException, InstantiationException {
        // 设置被代理对象
        targetClass = sayInterface;
        // 获取代理对象
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(targetClass.getClass());
        Class<?> clazz = proxyFactory.createClass();
        Object object = clazz.newInstance();
        ((ProxyObject) object).setHandler(this);
        return object;
    }

    // 直接操作字节码
    public static SayInterface createJavassistBytecodeDynamicProxy() throws Exception {
        ClassPool mPool = new ClassPool(true);
        CtClass mCtc = mPool.makeClass(SayInterface.class.getName() + "JavaassistProxy");
        mCtc.addInterface(mPool.get(SayInterface.class.getName()));
        mCtc.addConstructor(CtNewConstructor.defaultConstructor(mCtc));
        mCtc.addMethod(CtNewMethod.make("public void say() {System.out.println(\"said hello from javaassist\");}", mCtc));
        Class<?> pc = mCtc.toClass();
        return (SayInterface) pc.newInstance();
    }

}
