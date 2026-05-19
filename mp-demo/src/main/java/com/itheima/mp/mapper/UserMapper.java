package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> queryUserByIds(@Param("ids") List<Long> ids);

    @Update("UPDATE user SET balance = balance - #{amount} ${ew.customSqlSegment}")
    void updateBalanceByWrapper(@Param("amount") int amount, @Param("ew") QueryWrapper<User> wrapper);

    List<User> queryUsersByWrapper(@Param("ew") QueryWrapper<User> wrapper);


    @Update("UPDATE user SET balance = balance - #{amount} ${ew.customSqlSegment}")
    void deductBalanceById(@Param("amount") int amount, @Param("ew") LambdaQueryWrapper<User> userLambdaQueryWrapper);


    @Update("UPDATE user SET balance = balance - #{money} WHERE id = #{id}")
    void deductMoneyById(@Param("id") Long id, @Param("money") Integer money);
}
