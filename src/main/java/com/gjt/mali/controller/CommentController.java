package com.gjt.mali.controller;

import com.gjt.mali.exception.ExceptionEnums;
import com.gjt.mali.exception.MaLiException;
import com.gjt.mali.pojo.Comment;
import com.gjt.mali.pojo.User;
import com.gjt.mali.service.CommentService;
import com.gjt.mali.utils.CommentTypeEnum;
import com.gjt.mali.vo.CommentCreateVo;
import com.gjt.mali.vo.CommentVo;
import com.gjt.mali.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 问题回复处理
 * @author JianLaji
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping(value = "/comment")
    public ResultVo doPost(@RequestBody CommentCreateVo commentVo, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return new ResultVo(new MaLiException(ExceptionEnums.USER_DOES_NOT_EXIST));
        }
        if (StringUtils.isBlank(commentVo.getContent())){
            return new ResultVo(new MaLiException(ExceptionEnums.CONTENT_DOES_NOT));
        }
        Comment comment=new Comment();
        comment.setQuestionId(commentVo.getQuestionId());
        comment.setContent(commentVo.getContent());
        comment.setType(commentVo.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        ResultVo resultVo = commentService.creatComment(comment);
        return resultVo;
    }
    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultVo<List<CommentVo>> doComments(@PathVariable Integer id){
        List<CommentVo> commentVoList = commentService.queryAllcommentByParentId(id, CommentTypeEnum.COMMENT);
        return ResultVo.okOff(commentVoList);
    }
}

