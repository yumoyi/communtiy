package com.cy.community.dto;

import lombok.Data;

/**
 * @author cy
 * @since 2019-11-10 11:14
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
