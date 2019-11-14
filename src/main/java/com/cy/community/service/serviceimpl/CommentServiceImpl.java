package com.cy.community.service.serviceimpl;

import com.cy.community.dto.CommentDTO;
import com.cy.community.enums.CommentTypeEnum;
import com.cy.community.enums.NotificationStatusEnum;
import com.cy.community.enums.NotificationTypeEnum;
import com.cy.community.enums.CustomizeErrorCode;
import com.cy.community.exception.CustomizeException;
import com.cy.community.mapper.*;
import com.cy.community.pojo.*;
import com.cy.community.service.CommentService;
import com.cy.community.util.Asserts;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author cy
 * @since 2019-11-02 15:19
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    @Transactional
    public void insert(Comment comment, User commentator) {

        if (Objects.equals(comment.getParentId(), 0L)) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (Objects.isNull(comment.getType())|| !CommentTypeEnum.isExit(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType()) ) {
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());

            Asserts.isNull(dbComment, CustomizeErrorCode.COMMENT_NOT_FOUND);

            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            Asserts.isNull(question, CustomizeErrorCode.QUESTION_NOT_FOUND);

            commentMapper.insert(comment);

            // 增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.addCommentCount(parentComment);

            // 创建通知
            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
        } else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            Asserts.isNull(question,CustomizeErrorCode.QUESTION_NOT_FOUND);
            comment.setCommentCount(0);
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.addCommentCount(question);

            // 创建通知
            createNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
        }
    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        //去除自己给自己评价时显示通知
        if (Objects.equals(receiver, comment.getCommentator())) {
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    @Override
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (CollectionUtils.isEmpty(comments)) {
            return Collections.emptyList();
        }


        // 获取去重的评论人
        List<Long> userIds = comments.stream().map(Comment::getCommentator).distinct().collect(Collectors.toList());

        // 获取评论人并转换为 Map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);

        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, Function.identity()));

        // 转换 comment 为 commentDTO
        return comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

    }
}

