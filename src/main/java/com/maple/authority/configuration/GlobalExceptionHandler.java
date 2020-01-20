package com.maple.authority.configuration;


import com.maple.authority.exception.AuthorityException;
import com.maple.authority.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.validation.UnexpectedTypeException;
import java.nio.file.AccessDeniedException;

/**
 * description: 全局的的异常拦截器<br>
 * version: 1.0 <br>
 * date: 2019/2/14 11:19 <br>
 * author: vnaLc <br>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数非法异常.
     *
     * @param e the e
     * @return the wrapper
     */
    @ExceptionHandler(AuthorityException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultBean authorityException(AuthorityException e) {
        log.info("保存全局异常信息 ex={}", e.getMessage(), e);
        return ResultBean.getInstance().putCode(e.getCode()).putMessage(e.getMessage());
    }

    /**
     * 全局异常.
     *
     * @param e the e
     * @return the wrapper
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResultBean exception(Exception e) {
        log.info("保存全局异常信息 ex={}", e.getMessage(), e);
        return ResultBean.getInstance().error(e.getMessage());
    }

}
