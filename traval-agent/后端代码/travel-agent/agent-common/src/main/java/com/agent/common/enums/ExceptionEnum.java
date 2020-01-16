package com.agent.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    TRAVELLER_NOT_FOUND(404, "旅客没查到"),
    ADD_TRAVELLER_ERROR(500, "旅客添加失败"),
    ;
    private int code;
    private String msg;
}
