package com.market.cart.mapper;

import com.market.cart.Cart;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;

public interface CartMapper extends Mapper<Cart>, IdListMapper<Cart, Long>, DeleteByIdListMapper<Cart, Long> {
}
