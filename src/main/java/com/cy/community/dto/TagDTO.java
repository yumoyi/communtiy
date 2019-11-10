package com.cy.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author cy
 * @since 2019-11-07 14:12
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}