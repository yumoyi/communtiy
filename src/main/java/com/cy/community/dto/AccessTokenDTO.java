package com.cy.community.dto;

import lombok.Data;

/**
 * @author cy
 * @since 2019-09-24 16:04
 */
@Data
public class AccessTokenDTO {
    /**
     * 客户端id
     */
    private String client_id;

    /**
     * 客户端密钥
     */
    private String client_secret;
    private String code;
    /**
     * 重定向URI
     */
    private String redirect_uri;
    private String state;

}
