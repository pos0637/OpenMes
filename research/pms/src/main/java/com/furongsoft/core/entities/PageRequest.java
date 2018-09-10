package com.furongsoft.core.entities;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页列表请求
 *
 * @author Alex
 */
@Getter
@Setter
public class PageRequest {
    /**
     * 页码
     */
    private int pageNum;

    /**
     * 页内数据长度
     */
    private int pageSize;

    /**
     * 获取分页对象
     *
     * @return 分页对象
     */
    public <T> Page<T> getPage() {
        return new Page<T>(pageNum, pageSize);
    }
}
