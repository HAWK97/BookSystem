package com.hawk.book.exception;

import com.hawk.book.data.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理类
 *
 * @author wangshuguang
 * @since 2018/01/04
 */
// 把 @ControllerAdvice 注解内部使用 @ExceptionHandler、@InitBinder、@ModelAttribute 注解的方法
// 应用到所有的 @RequestMapping 注解的方法
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    // 定义当出现特定的异常时进行处理的方法
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseMessage handle(Exception e) {
        if (e instanceof MyException) {
            MyException myException = (MyException) e;
            return ResponseMessage.error(myException.getCode(), myException.getMessage());
        } else {
            log.error("[系统异常] {}", e);
            return ResponseMessage.error(666, "运行时异常！");
        }
    }
}
