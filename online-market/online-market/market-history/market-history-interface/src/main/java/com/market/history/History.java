package com.market.history;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="history")
public class History {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long user_id;
    private Long item_id;
    private Long count;
}
