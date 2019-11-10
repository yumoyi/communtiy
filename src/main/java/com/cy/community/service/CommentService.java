package com.cy.community.service;

import com.cy.community.dto.CommentDTO;
import com.cy.community.enums.CommentTypeEnum;
import com.cy.community.pojo.Comment;
import com.cy.community.pojo.User;

import java.util.List;

/**
 * @author cy
 * @since 2019-11-02 15:19
 */
public interface CommentService {
    void insert(Comment comment,User commentator);

    List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type);

}

