package org.luoyuhan.learn.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author luoyuhan
 * @since 2024/1/23
 */
@Slf4j
@Service
public class ClassModify {
    @Resource
    private BaseBiz baseBiz;

    public String getPublicModify(String s) {
        return publicModify(s);
    }

    public String getPrivateModify(String s) {
        return privateModify(s);
    }

    public String publicModify(String s) {
        log.info("modify time: {}", baseBiz.getTime());
        return s + " public modified";
    }

    private String privateModify(String s) {
        log.info("modify time: {}", baseBiz.getTime());
        return s + " private modified";
    }
}
