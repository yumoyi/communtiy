package com.cy.community.enums;

/**
 * @author cy
 * @since 2019-11-07 16:19
 */
public enum NotificationStatusEnum {
    UNREAD(0), READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}