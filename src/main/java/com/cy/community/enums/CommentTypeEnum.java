package com.cy.community.enums;

/**
 * @author cy
 * @since 2019-11-02 15:05
 */
public enum CommentTypeEnum {
    //问题类型
    QUESTION(1),
    //评价类型
    COMMENT(2)
    ;

    private Integer type;

    public static boolean isExit(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType().equals(type)){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
