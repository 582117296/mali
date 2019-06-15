package com.gjt.mali.service;

import com.gjt.mali.pojo.Comment;
import com.gjt.mali.utils.CommentTypeEnum;
import com.gjt.mali.vo.CommentVo;
import com.gjt.mali.vo.ResultVo;

import java.util.List;

public interface CommentService {
    ResultVo creatComment(Comment comment);

    List<CommentVo> queryAllcommentByParentId(Integer id, CommentTypeEnum typeEnum);
}
