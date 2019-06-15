package com.gjt.mali.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ExceptionEnums {
    THIS_QUESTION_DOES_NOT_EXIST("您查询的问题不存在哦" ,404),
    INSERT_DOES_NOT("添加失败了!!请重试",500),
    USER_DOES_NOT_EXIST("未登录不可评论哦!请先登录",2000),
    USER_IS_NOT("用户没有登录!请先登录!",2001),
    QUESTION_GO_OUT("该问题不存在,可能游走了!",2002),
    TYPE_PARAM_WRONG("评论类型错误!",2003),
    COMMENT_GO_OUT("该回复已经游走了",2004),
    ADD_IS_FALSE("添加回复失败!",2005),
    DOES_NOT_COMMENT("不可对它进行评论哦!",2006),
    CONTENT_DOES_NOT("您还没没有输入回复内容呢1",2007)
    ;
    private String massage;
    private Integer code;

}
