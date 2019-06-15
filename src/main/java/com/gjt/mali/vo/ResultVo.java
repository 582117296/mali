package com.gjt.mali.vo;

import com.gjt.mali.exception.ExceptionEnums;
import com.gjt.mali.exception.MaLiException;
import lombok.Data;

@Data
public class ResultVo<T> {
    private String massage;
    private int code;
    private Long timeStamp;
    private T data;

    public ResultVo(ExceptionEnums enums) {
        this.massage = enums.getMassage();
        this.code = enums.getCode();
        this.timeStamp = System.currentTimeMillis();
        throw new MaLiException(enums);
    }
    public ResultVo(MaLiException e) {
        this.massage = e.getEnums().getMassage();
        this.code = e.getEnums().getCode();
        this.timeStamp = System.currentTimeMillis();
    }
    public ResultVo() {
    }
    public static ResultVo okHttp(){
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(200);
        resultVo.setMassage("请求成功");
        resultVo.setTimeStamp(System.currentTimeMillis());
        return resultVo;
    }

    public static <T> ResultVo okOff(T result) {
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(200);
        resultVo.setMassage("请求成功");
        resultVo.setData(result);
        resultVo.setTimeStamp(System.currentTimeMillis());
        return resultVo;
    }
}
