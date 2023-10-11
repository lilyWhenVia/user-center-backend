package com.catGod.commons;

/**
 * Created by lily via on 2023/10/7 16:57
 */

import com.catGod.constant.ErrorCode;

/**
 * @description: 获取、使用全局响应处理器的工具类
 * @author Lily Via
 * @date 2023/10/7 16:57
 * @version 1.0
 */
public class ResponseUtil {

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<T>(ErrorCode.SUCCESS.getCode(), data, ErrorCode.SUCCESS.getMessage());
    }

    /**
     * @description: 全局异常处理器接受异常时返回error方法
     * @author Lily Via
     * @date 2023/10/8 17:01
     * @version 1.0
     */
    public static <T> BaseResponse<T> error(String code, String message, String description){
        return new BaseResponse<T>(code, message, description);
    }

    public static <T> BaseResponse<T> unLogin(){
        return new BaseResponse<T>(ErrorCode.UN_LOGIN.getCode(), ErrorCode.UN_LOGIN.getMessage());
    }

    public static <T> BaseResponse<T> wrongParam(String description){
        return new BaseResponse<T>(ErrorCode.WRONG_PARAM_ERR.getCode(), ErrorCode.WRONG_PARAM_ERR.getMessage(), description);
    }

    public static <T> BaseResponse<T> accountNotFound(String description){
        return new BaseResponse<T>(ErrorCode.ACCOUNT_NOT_FOUND_CODE.getCode(), ErrorCode.ACCOUNT_NOT_FOUND_CODE.getMessage(), description);
    }

    public static <T> BaseResponse<T> unAuth(){
        return new BaseResponse<T>(ErrorCode.WRONG_PARAM_ERR.getCode(), ErrorCode.WRONG_PARAM_ERR.getMessage());
    }


    public static <T> BaseResponse<T> error(ErrorCode e, String description){
        return new BaseResponse<T>(e.getCode(), e.getMessage(), description);
    }






}
