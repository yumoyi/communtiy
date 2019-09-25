package com.cy.community.dto;

import lombok.Data;

/**
 * @author cy
 * @since 2019-09-24 16:04
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
