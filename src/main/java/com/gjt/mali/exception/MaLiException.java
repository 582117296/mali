package com.gjt.mali.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaLiException extends RuntimeException{
    private ExceptionEnums enums;
}
