package com.gjt.mali.service.serviceImpl;

import com.gjt.mali.exception.ExceptionEnums;
import com.gjt.mali.exception.MaLiException;
import com.gjt.mali.mapper.CommentMapper;
import com.gjt.mali.mapper.QuestionMapper;
import com.gjt.mali.mapper.UserMapper;
import com.gjt.mali.pojo.*;
import com.gjt.mali.service.CommentService;
import com.gjt.mali.utils.CommentTypeEnum;
import com.gjt.mali.vo.CommentVo;
import com.gjt.mali.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<CommentVo> queryAllcommentByParentId(Integer id, CommentTypeEnum typeEnum) {
        CommentExample ce=new CommentExample();
        ce.setOrderByClause("id DESC");
        ce.createCriteria().andQuestionIdEqualTo(Long.valueOf(id)).andTypeEqualTo(typeEnum.getType());
        List<Comment> comments = commentMapper.selectByExample(ce);
        if (comments.isEmpty()){
            return new ArrayList<>();
        }
        //获取并去重评论人
        Set<Integer> collect = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds=new ArrayList<>();
        userIds.addAll(collect);

        UserExample ue=new UserExample();
        ue.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(ue);
        Map<Integer, User> usermap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<CommentVo> commentVoList=comments.stream().map(comment -> {
            CommentVo commentVo=new CommentVo();
            BeanUtils.copyProperties(comment, commentVo);
            commentVo.setUser(usermap.get(comment.getCommentator()));
            return commentVo;
        }).collect(Collectors.toList());
        return commentVoList;
    }

    @Override
    //配置事物
    @Transactional
    public ResultVo creatComment(Comment comment) {
        if (comment.getQuestionId()==null || comment.getQuestionId()==0){
            return new ResultVo(new MaLiException(ExceptionEnums.DOES_NOT_COMMENT));
        }
        if (comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            return new ResultVo(new MaLiException(ExceptionEnums.QUESTION_GO_OUT));
        }
        int insert;
        int num;
        if (CommentTypeEnum.QUESTION.getType().equals(comment.getType())){
            //评论提问
            Question question = questionMapper.selectByPrimaryKey(Math.toIntExact(comment.getQuestionId()));
            if (question==null){
                return new ResultVo(new MaLiException(ExceptionEnums.QUESTION_GO_OUT));
            }
            insert = commentMapper.insert(comment);
            question.setCommentCount(question.getCommentCount()+1);
            num=questionMapper.updateByPrimaryKey(question);
            if (insert<=0 || num<=0){
                return new ResultVo(new MaLiException(ExceptionEnums.INSERT_DOES_NOT));
            }
        }else {
            //回复评论
            Comment parent = commentMapper.selectByPrimaryKey(comment.getQuestionId());
            if (parent==null){
                return new ResultVo(new MaLiException(ExceptionEnums.COMMENT_GO_OUT));
            }
            insert = commentMapper.insert(comment);
            //增加评论数
            if (parent.getCommentCount()==null){
                parent.setCommentCount(1L);
            }else {
                parent.setCommentCount(parent.getCommentCount()+1);
            }
            num = commentMapper.updateByPrimaryKey(parent);
            if (insert<=0|| num<=0){
                return new ResultVo(new MaLiException(ExceptionEnums.ADD_IS_FALSE));
            }
        }
        return ResultVo.okHttp();
    }
}
