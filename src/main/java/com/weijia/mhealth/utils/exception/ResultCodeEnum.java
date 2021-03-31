package com.weijia.mhealth.utils.exception;

import lombok.Getter;

/**
 * 结果类枚举
 * */
@Getter
public enum ResultCodeEnum {
    SUCCESS(true,200,"成功"),
    ERROR(true,201,"查询结果为空"),
    UNKNOWN_ERROR(false,202,"未知错误"),
    PARAM_ERROR(false,203,"参数错误"),
    NULL_POINT(false,204,"空指针异常"),
    HTTP_CLIENT_ERROR(false,205,"数组下标越界"),
    UPDATE_STU_STATE_IN_DB(false,206,"修改数据库学生在线状态错误"),
    UPDATE_STU_STATE_IN_REDIS(false,206,"Redis修改学生在线状态错误"),
    UPDATE_DOCTOR_STATE_IN_REDIS(false,207,"Redis修改医生在线状态错误"),
    UPDATE_DOCTOR_STATE_IN_DB(false,207,"db修改医生在线状态错误"),
    ;

    // 响应是否成功
    private Boolean success;
    // 响应状态码
    private Integer code;
    // 响应信息
    private String message;

    ResultCodeEnum(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}