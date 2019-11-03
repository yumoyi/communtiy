package com.cy.community.controller;

import com.cy.community.dto.PaginationDTO;
import com.cy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author cy
 * @since 2019-09-16 16:17
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "1")Integer page,
                        @RequestParam(name="size",defaultValue = "2")Integer size) {
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
