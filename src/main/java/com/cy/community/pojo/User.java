package com.cy.community.pojo;

import lombok.Data;

/**
 * @author cy
 * @since 2019-09-25 11:18
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
}
