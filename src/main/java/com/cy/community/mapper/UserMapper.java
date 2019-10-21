package com.cy.community.mapper;

import com.cy.community.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author cy
 * @since 2019-09-25 11:02
 */
@Mapper
public interface UserMapper {

    /**
     * 添加
     * @param user 用户信息
     */
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    /**
     * 验证用户是否登录成功
     * @param token 用于验证用户是否登录成功
     * @return 用户信息
     */
    @Select("select * from user where token = #{token}")
    User findByToken(String token);

}
