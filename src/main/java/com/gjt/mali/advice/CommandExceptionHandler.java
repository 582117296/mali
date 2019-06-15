package com.gjt.mali.advice;

import com.gjt.mali.exception.ExceptionEnums;
import com.gjt.mali.exception.MaLiException;
import com.gjt.mali.vo.ResultVo;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class CommandExceptionHandler {
    @ExceptionHandler(MaLiException.class)
    public Object handleControllerException(Model model, MaLiException maliException,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        ExceptionEnums enums = maliException.getEnums();
        if("application/json;charset=UTF-8".equals(request.getContentType())){
            return ResponseEntity.status(enums.getCode()).body(new ResultVo(enums));
        } else {
            model.addAttribute("errorCode", enums.getCode());
            model.addAttribute("errorMassage", enums.getMassage());
        }
        return new ModelAndView("error");
    }
}
