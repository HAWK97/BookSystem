package com.hawk.book.exception;


import com.hawk.book.data.ResultEnum;

/**
 * 自定义异常类
 *
 * @author wangshuguang
 * @since 2018/01/04
 */
public class MyException extends RuntimeException {

    private Integer code;

    public MyException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
