package com.cy.community.service;

import com.cy.community.dto.PaginationDTO;
import com.cy.community.dto.QuestionDTO;
import com.cy.community.pojo.Question;

import java.util.List;

/**
 * @author cy
 * @since 2019-10-22 20:35
 */

public interface QuestionService {
    /**
     * 带分页的问题查询
     * @param page 起始数据
     * @param size 每页数据
     * @return 问题列表
     */
    PaginationDTO list(String search,Integer page, Integer size);

    PaginationDTO listByUserId(Long userId, Integer page, Integer size);

    QuestionDTO getById(Long id);

    void createOrUpdate(Question question);

    void addView(Long id);

    List<QuestionDTO> selectRelated(QuestionDTO queryDTO);
}
