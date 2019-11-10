package com.cy.community.dto;

import lombok.Data;

/**
 * @author cy
 * @since 2019-11-02 14:09
 */
@Data
public class CommentCreateDTO {

    private Long parentId;
    private Integer type;
    private String content;
}
