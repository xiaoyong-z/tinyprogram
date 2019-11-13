package com.market.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    USER_NOT_FOUND(404, "用户没查到"),
    ITEM_NOT_FOUND(404, "商品没查到"),
    SPEC_ITEM_NOT_FOUND(404, "指定商品没查到"),
    CART_NOT_FOUND(404, "指定商品购物车没记录"),
    HISTORY_NOT_FOUND(404, "用户没有历史记录"),
    USER_REGISTER_ERROR(500, "用户注册失败"),
    USER_BUY_ERROR(500, "用户购买失败"),
    USER_TOPUP_ERROR(500, "用户充值失败"),
    ITEM_ADD_ERROR(500, "商品添加失败"),
    ITEM_BUY_ERROR(500, "商品购买失败"),
    ITEM_TAKEOFF_ERROR(500, "商品下降失败"),
    ITEM_ADDSTOCK_ERROR(500, "商品补货失败"),
    ADD_CART_ERROR(500, "购物车添加失败"),
    DELETE_CART_ERROR(500, "购物车删除失败"),
    DELETE_BATCH_CART_ERROR(500, "购物车批量删除失败"),
    UPDATE_CART_ERROR(500, "购物车更新失败"),
    ADD_HISTORY_ERROR(500, "历史记录增加失败"),
    ;
    private int code;
    private String msg;
}
