package com.gjt.mali.utils;

import lombok.Getter;

@Getter
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2)
    ;
    private Integer type;
    CommentTypeEnum(Integer type) {
        this.type = type;
    }
//判断是否存在
    public static boolean isExist(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType().equals( type)){
                return true;
            }
        }
        return false;
    }
}
