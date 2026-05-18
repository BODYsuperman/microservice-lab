package com.itheima.mp.domain.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

// 贫血模型 、 充血模型
@Data
public class PageQuery {
    private Integer pageNo = 1;
    private Integer pageSize = 5;
    private String sortBy;
    private Boolean isAsc = false;

    // 把当前query构造成MyBatisPlus的 Page
    public <T> Page<T> toMpPage(String defaultSortBy, boolean isAsc){
        // 1.分页条件
        Page<T> p = Page.of(pageNo, pageSize);
        // 2.排序条件
        if(StrUtil.isNotBlank(sortBy)) {
            p.addOrder(new OrderItem(sortBy, isAsc));
        }else{
            p.addOrder(new OrderItem(defaultSortBy, isAsc));
        }
        return p;
    }

    // 把当前query构造成MyBatisPlus的 Page
    public <T> Page<T> toMpPage(OrderItem ... defaultOrderItems){
        // 1.分页条件
        Page<T> p = Page.of(pageNo, pageSize);
        // 2.排序条件
        if(StrUtil.isNotBlank(sortBy)) {
            p.addOrder(new OrderItem(sortBy, isAsc));
            return p;
        }
        // 3.默认排序
        for (OrderItem order : defaultOrderItems) {
            p.addOrder(order);
        }
        return p;
    }

    // 把当前query构造成MyBatisPlus的 Page
    public <T> Page<T> toMpPageDefaultSortByUpdate(){
        // 1.分页条件
        Page<T> p = Page.of(pageNo, pageSize);
        // 2.排序条件
        if(StrUtil.isNotBlank(sortBy)) {
            p.addOrder(new OrderItem(sortBy, isAsc));
        }else{
            p.addOrder(new OrderItem("update_time", false));
        }
        return p;
    }
}
