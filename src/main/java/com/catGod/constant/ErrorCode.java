package com.catGod.constant;

/**
 * Created by lily via on 2023/10/7 17:43
 */
public enum ErrorCode {

    SUCCESS(UserConstant.SUCCESS_CODE, "请求成功"),
    SYSTEM_ERR(UserConstant.SYSTEM_ERR_CODE, "系统异常"),
    UN_AUTH(UserConstant.UN_AUTH_CODE, "无权限"),
    UN_LOGIN(UserConstant.UN_LOGIN_CODE, "未登录状态"),
    ACCOUNT_NOT_FOUND_CODE(UserConstant.ACCOUNT_NOT_FOUND_CODE, "账号不存在"),
    WRONG_PARAM_ERR(UserConstant.WRONG_PARAM_ERR_CODE, "参数错误"),
    ;

    private String code;
    private String message;

    private String description;

    ErrorCode(String code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

}
