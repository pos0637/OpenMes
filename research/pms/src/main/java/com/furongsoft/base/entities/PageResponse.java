package com.furongsoft.base.entities;

import com.baomidou.mybatisplus.plugins.Page;
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
    private int total;

    public PageResponse(HttpStatus status, Page<T> page) {
        super(status, null, page.getRecords());
        setTotal(page.getTotal());
    }
}
