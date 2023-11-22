package org.luoyuhan.learn.component;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author luoyuhan
 * @since 2023/11/22
 */
@Component
public class BaseBiz {
    public String getTime() {
        return new Date().toString();
    }
}
