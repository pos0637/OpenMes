package com.furongsoft.base.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * 分页列表请求返回数据
 *
 * @author Alex
 */
@Getter
@Setter
public class PageResponse<T> extends RestResponse {
    /**
     * 记录总数
     */
    private long total;

    public PageResponse(HttpStatus status, com.baomidou.mybatisplus.plugins.Page<T> page) {
        super(status);
        setData(page.getRecords());
        setTotal(page.getTotal());
    }

    public PageResponse(HttpStatus status, org.springframework.data.domain.Page<T> page) {
        super(status);
        setData(page.getContent());
        setTotal(page.getTotalElements());
    }
}
