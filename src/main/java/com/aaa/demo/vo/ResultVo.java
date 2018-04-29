package com.aaa.demo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by ruosu on 2018/4/26.
 */
@Data
public class ResultVo implements Serializable {

    private String message;

    private Boolean success = true;

    public static Boolean isSuccess(ResultVo vo) {
        return vo.getSuccess();
    }

    public ResultVo(String message) {
       this.message = message;
    }

    public ResultVo(Boolean isSuccess, String message) {
        this.success = isSuccess;
        this.message = message;
    }
}

