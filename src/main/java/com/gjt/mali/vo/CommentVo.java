package com.gjt.mali.vo;

import com.gjt.mali.pojo.User;
import lombok.Data;

@Data
public class CommentVo {
    private Long id;
    private Long questionId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private Long commentCount;
    private User user;
}
