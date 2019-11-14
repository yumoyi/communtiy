package com.cy.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author cy
 * @since 2019-11-07 14:12
 */
@Data
public class TagDTO {
    /**
     * 标签类名字
     */
    private String categoryName;
    /**
     * 标签名字
     */
    private List<String> tags;
}