package com.cy.community.interceptor;

import com.cy.community.mapper.UserMapper;
import com.cy.community.pojo.User;
import com.cy.community.pojo.UserExample;
import com.cy.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * @author cy
 * @since 2019-10-31 18:24
 */

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies) && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                String i = "token";
                if (cookie.getName().equals(i)) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);

                    if (!Objects.equals(0,users.size())) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", users.get(0));
                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                        session.setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }
}
