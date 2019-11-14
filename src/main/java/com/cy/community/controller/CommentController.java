package com.cy.community.controller;

import com.cy.community.dto.CommentCreateDTO;
import com.cy.community.dto.CommentDTO;
import com.cy.community.dto.ResultDTO;
import com.cy.community.enums.CommentTypeEnum;
import com.cy.community.enums.CustomizeErrorCode;
import com.cy.community.pojo.Comment;
import com.cy.community.pojo.User;
import com.cy.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @author cy
 * @since 2019-11-02 14:10
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/comment")
    @ResponseBody
    public ResultDTO post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request){
        User user = (User ) request.getSession().getAttribute("user");
        if(Objects.isNull(user)){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);

        }

        if(Objects.isNull(commentCreateDTO) || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        commentService.insert(comment,user);
        return ResultDTO.okOf();
    }


    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO comments(@PathVariable(name ="id")Long id){
        List<CommentDTO> commentsDTO = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentsDTO);
    }
}
