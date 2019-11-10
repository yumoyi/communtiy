package com.cy.community.mapper;

import com.cy.community.dto.QuestionQueryDTO;
import com.cy.community.pojo.Question;

import java.util.List;

public interface QuestionExtMapper {
    int addView(Question record);
    int addCommentCount(Question record);
    List<Question> selectRelated(Question question);


    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}