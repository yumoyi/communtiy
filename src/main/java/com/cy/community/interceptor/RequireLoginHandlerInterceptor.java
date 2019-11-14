package com.cy.community.interceptor;

import com.cy.community.annotation.RequireLogin;
import com.cy.community.enums.CustomizeErrorCode;
import com.cy.community.exception.RequireLoginException;
import com.cy.community.pojo.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author cy
 * @since 2019-11-10 14:43
 */
public class RequireLoginHandlerInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequireLogin requireLogin = handlerMethod.getMethodAnnotation(RequireLogin.class);
            if (Objects.nonNull(requireLogin)) {
                User user = (User) request.getSession().getAttribute("user");
                if(Objects.isNull(user)){
                    throw new RequireLoginException(CustomizeErrorCode.NO_LOGIN);
                }
            }
        }
        return true;
    }
}
