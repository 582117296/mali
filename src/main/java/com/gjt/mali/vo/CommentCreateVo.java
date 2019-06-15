package com.gjt.mali.vo;

import lombok.Data;

@Data
public class CommentCreateVo {
    private Long questionId;
    private String content;
    private Integer type;

}
