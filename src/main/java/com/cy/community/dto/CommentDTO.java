package com.cy.community.dto;

import com.cy.community.pojo.User;
import lombok.Data;

/**
 * @author cy
 * @since 2019-11-05 19:49
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
    private Integer commentCount;

}
