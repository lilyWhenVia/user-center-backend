package com.catGod.commons;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lily via on 2023/10/7 16:50
 */
/**
 * @description: 全局相应处理器
 * @author Lily Via
 * @date 2023/10/7 16:51
 * @version 1.0
 */
@Data
public class BaseResponse<T> implements Serializable {

    private String code;
    private T data;
    private String message;
    private String description;

    /**
     * @description: description可为空
     * @author Lily Via
     * @date 2023/10/7 16:56
     * @version 1.0
     */
    public BaseResponse(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = "";
    }
/**
 * @description: 全参构造器
 * @author Lily Via
 * @date 2023/10/7 16:55
 * @version 1.0
 */
    public BaseResponse(String code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    /**
     * @description:
            * @param: 传输数据可为空
            * @return:
            * @author lily via
            * @date: 2023/10/7 16:54
     */
    public BaseResponse(String code, String message, String description) {
        this.data = null;
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(String code, String message) {
        this.data = null;
        this.code = code;
        this.message = message;
        this.description = "";
    }

    public BaseResponse(String code) {
        this.data = null;
        this.message = "";
        this.description = "";
        this.code = code;
    }


}
