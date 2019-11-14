package com.cy.community.enums;

import com.cy.community.exception.ICustomerErrorCode;

/**
 * @author cy
 * @since 2019-11-02 9:37
 */
public enum CustomizeErrorCode implements ICustomerErrorCode {
    //错误信息1:问题找不到
    QUESTION_NOT_FOUND(2001, "你找的问题已经不在了,要不要换个试一试?"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何一个问题进行回复"),
    NO_LOGIN(2003, "当前操作需要登录,请先登录后重试!"),
    SYS_ERROR(2004, "服务器起火啦,请稍后再试!!!"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或者不存在!"),
    COMMENT_NOT_FOUND(2006, "你操作的评论不存在,要不要换个试一试?"),
    COMMENT_IS_EMPTY(2007, "输入的内容不能为空!"),
    READ_NOTIFICATION_FAIL(2008, "兄弟你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了？"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败"),
    INVALID_INPUT(2011, "非法输入"),
    INVALID_OPERATION(2012, "兄弟，是不是走错房间了？"),
    ;

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
