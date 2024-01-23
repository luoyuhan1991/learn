package org.luoyuhan.learn.controller;

import org.luoyuhan.learn.component.ClassModify;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author luoyuhan
 * @since 2024/1/23
 */
@RestController
public class TestController {
    @Resource
    private ClassModify classModify;

    @GetMapping("/testModify")
    public String testModify(String s) {
        return classModify.publicModify(s);
    }
}
