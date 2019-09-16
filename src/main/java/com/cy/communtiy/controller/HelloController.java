package com.cy.communtiy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author cy
 * @since 2019-09-16 16:17
 */
@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name",defaultValue = "我的世界")String name, Model model){
        model.addAttribute("name",name);
        return "hello";
    }
    @RequestMapping("/hellos")
    public String hellos(@RequestParam(value = "name",defaultValue = "我的世界2")String name, Model model){
        model.addAttribute("name",name);
        return "test/helloTest";
    }
}
