package org.example.admin.controller;

import org.example.framework.common.base.Result;
import org.example.framework.security.core.annotation.Anonymous;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @GetMapping("/hi")
    @Anonymous
    public Result<String> hi() {
        return Result.ok("hi!");
    }
}
