package com.itheima.mp.domain.vo;

import com.itheima.mp.domain.po.UserInfo;
import com.itheima.mp.enums.UserStatus;
import lombok.Data;

@Data
public class UserVO {
    private Long id;
    private String username;
    private UserInfo info;
    private UserStatus status;
    private Long balance;
}
