package com.yuqiong.college.common.handler;

import com.yuqiong.college.common.utils.ExceptionUtil;
import com.yuqiong.college.common.utils.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 *
 * @author LENOVO
 * @version 1.0
 * @date 2021/2/1
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultData error(GuliException e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return ResultData.error().message(e.getMessage()).code(e.getCode());
    }
}
