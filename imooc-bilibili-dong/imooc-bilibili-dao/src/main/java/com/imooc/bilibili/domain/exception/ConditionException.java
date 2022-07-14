package com.imooc.bilibili.domain.exception;

import lombok.Data;

@Data
public class ConditionException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String code;

    public ConditionException(String code, String name){
        super(name);
        this.code = code;
    }

    public ConditionException(String name){
        super(name);
        code = "500";
    }
}
