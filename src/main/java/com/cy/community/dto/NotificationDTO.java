package com.cy.community.dto;

import lombok.Data;

/**
 * @author cy
 * @since 2019-11-07 16:57
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
}
