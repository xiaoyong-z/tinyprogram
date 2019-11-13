package com.market.cart;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="cart")
public class Cart {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long user_id;
    private Long item_id;
    private Long count;
}
