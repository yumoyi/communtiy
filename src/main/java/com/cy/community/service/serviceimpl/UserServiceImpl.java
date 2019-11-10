package com.cy.community.service.serviceimpl;

import com.cy.community.mapper.UserMapper;
import com.cy.community.pojo.User;
import com.cy.community.pojo.UserExample;
import com.cy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(users.size()==0){
            //添加
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            User dbUser = users.get(0);
            User updateUSer = new User();
            updateUSer.setGmtCreate(System.currentTimeMillis());
            updateUSer.setAvatarUrl(user.getAvatarUrl());
            updateUSer.setName(user.getName());
            updateUSer.setToken(user.getToken());
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUSer, userExample);
        }

    }
}
