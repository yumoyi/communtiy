package com.cy.community.controller;

import com.cy.community.dto.QuestionDTO;
import com.cy.community.mapper.QuestionMapper;
import com.cy.community.mapper.UserMapper;
import com.cy.community.pojo.Question;
import com.cy.community.pojo.User;
import com.cy.community.service.QuestionService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author cy
 * @since 2019-09-16 16:17
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies !=null&&cookies.length!=0) {
            for (Cookie cookie : cookies) {
                String i = "token";
                if (cookie.getName().equals(i)) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionDTOS = questionService.list();
        model.addAttribute("questions",questionDTOS);
        return "index";
    }
}
