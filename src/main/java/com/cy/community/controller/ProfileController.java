package com.cy.community.controller;

import com.cy.community.annotation.RequireLogin;
import com.cy.community.dto.PaginationDTO;
import com.cy.community.pojo.User;
import com.cy.community.service.NotificationService;
import com.cy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


/**
 * @author cy
 * @since 2019-10-26 17:58
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;


    @RequireLogin
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          HttpServletRequest request,
                          @RequestParam(name="page",defaultValue = "1")Integer page,
                          @RequestParam(name="size",defaultValue = "2")Integer size,
                          Model model){

        User user = (User) request.getSession().getAttribute("user");

        if(Objects.equals("questions", action)) {
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.listByUserId(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        }else if (Objects.equals("replies",action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        }


        return "profile";
    }
}
