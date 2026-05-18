package com.itheima.mp.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class PageVO<T> {
    private Long total;
    private Long pages;
    private List<T> list;

    public <P> PageVO(Page<P> page, Class<T> clazz) {
        this.total = page.getTotal();
        this.pages = page.getPages();
        List<P> records = page.getRecords();
        if (CollUtil.isEmpty(records)) {
            list = Collections.emptyList();
            return;
        }
        this.list = BeanUtil.copyToList(records, clazz);
    }

    public <P> PageVO(Page<P> page, Function<P, T> convertor) {
        this.total = page.getTotal();
        this.pages = page.getPages();
        List<P> records = page.getRecords();
        if (CollUtil.isEmpty(records)) {
            list = Collections.emptyList();
            return;
        }
        this.list = records.stream().map(convertor).collect(Collectors.toList());
    }
}
