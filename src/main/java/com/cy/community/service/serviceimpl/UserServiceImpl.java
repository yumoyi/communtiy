package com.cy.community.service.serviceimpl;

import com.cy.community.mapper.UserMapper;
import com.cy.community.pojo.User;
import com.cy.community.pojo.UserExample;
import com.cy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author cy
 * @since 2019-11-01 14:41
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);
        if(Objects.equals(users.size(),0)){
            //添加
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setGmtCreate(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, userExample);
        }

    }
}
