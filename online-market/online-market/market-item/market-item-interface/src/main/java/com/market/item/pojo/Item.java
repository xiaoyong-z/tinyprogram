package com.market.item.pojo;


import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "item")
public class Item {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;
    private String category;
    private String img_url;
    private Long price;
    private Long belong_id;
    private Long stock;
    private Boolean on_sale;
    private String tips;
}
