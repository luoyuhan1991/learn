package org.luoyuhan.learn.java.proxypattern;

/**
 * @author luoyuhan
 */
public class SayHelloService implements SayInterface {
    @Override
    public void say() {
        System.out.println("said hello from implemented class");
    }
}

