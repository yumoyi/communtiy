package com.cy.community.service.serviceimpl;

import com.cy.community.dto.PaginationDTO;
import com.cy.community.dto.QuestionDTO;
import com.cy.community.dto.QuestionQueryDTO;
import com.cy.community.enums.CustomizeErrorCode;
import com.cy.community.exception.CustomizeException;
import com.cy.community.mapper.QuestionExtMapper;
import com.cy.community.mapper.QuestionMapper;
import com.cy.community.mapper.UserMapper;
import com.cy.community.pojo.Question;
import com.cy.community.pojo.QuestionExample;
import com.cy.community.pojo.User;
import com.cy.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
/**
 * @author cy
 * @since 2019-10-22 20:38
 */
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Override
    public PaginationDTO list(String search,Integer page, Integer size) {

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = String.join("|", tags);
        }

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);

        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);

        // 计算共有多少页
        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size+1;
        }
        //控制页码数不超出范围
        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);
        //给limit的起始值
        Integer offset = size*(page-1);


        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);


        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    @Override
    public PaginationDTO listByUserId(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(example);

        // 计算共有多少页
        if (Objects.equals(totalCount % size, 0)) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        //控制页码数不超出范围
        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);
        //给limit的起始值
        Integer offset = size*(page-1);

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    @Override
    public QuestionDTO getById(Long id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        if(Objects.isNull(question)){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void createOrUpdate(Question question) {
        if(Objects.isNull(question.getId())){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }else {
            //更新

            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int i = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if(i!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    @Override
    public void addView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.addView(question);
    }

    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if(StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = String.join("|", tags);
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionsDTO = questions
                .stream()
                .map(q -> {
                    QuestionDTO questionDTO = new QuestionDTO();
                    BeanUtils.copyProperties(q, questionDTO);
                 return questionDTO;
                }).collect(Collectors.toList());
        return questionsDTO;
    }
}
