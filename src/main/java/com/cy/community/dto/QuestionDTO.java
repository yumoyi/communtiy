package com.cy.community.dto;

import com.cy.community.pojo.User;
import lombok.Data;

/**
 * @author cy
 * @since 2019-10-22 20:34
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
