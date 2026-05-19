package com.itheima.mp.domain.query;

import lombok.Data;

@Data
public class UserQuery {
    private String name;
    private Integer status;
    private Integer minBalance;
    private Integer maxBalance;
}
