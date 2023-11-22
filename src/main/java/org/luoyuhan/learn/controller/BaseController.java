package org.luoyuhan.learn.controller;

import org.luoyuhan.learn.component.BaseBiz;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author luoyuhan
 * @since 2023/11/22
 */
@RestController
public class BaseController {
    @Resource
    private BaseBiz baseBiz;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/test2")
    public String test2() {
        return baseBiz.getTime();
    }
}
