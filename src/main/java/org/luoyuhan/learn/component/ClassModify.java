package org.luoyuhan.learn.component;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

/**
 * @author luoyuhan
 * @since 2024/1/23
 */
@Service
public class ClassModify {
    public String publicModify(String s) {
        return "public " + modify(s);
    }

    private String privateModify(String s) {
        return "private " + modify(s);
    }

    public String modify(String s) {
        Object currentProxy = AopContext.currentProxy();

        return s + " modified";
    }
}
