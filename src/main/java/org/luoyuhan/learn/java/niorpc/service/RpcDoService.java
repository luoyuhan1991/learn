package org.luoyuhan.learn.java.niorpc.service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author luoyuhan
 */
@Slf4j
public class RpcDoService {
    public String doSomething(String thing) {
        String result = "do " + thing;
        log.info("RpcDoService, result:{}", result);
        return result;
    }
}
