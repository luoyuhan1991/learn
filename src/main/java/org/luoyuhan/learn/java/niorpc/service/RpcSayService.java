package org.luoyuhan.learn.java.niorpc.service;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author luoyuhan
 */
@Slf4j
public class RpcSayService {
    public String saySomething(String name) throws InterruptedException {
        String result = "hello, " + name;
        TimeUnit.SECONDS.sleep(5);
        log.info("saySomething, result:{}", result);
        return result;
    }
}
