package com.aim.questionnaire.entity.po;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public class PageInfoObject<T> {
    private PageInfo<T> page;
    private Map<String, Object> queryParam;

    public PageInfo<T> getPage() {
        return page;
    }

    public void setPage(PageInfo<T> page) {
        this.page = page;
    }

    public Map<String, Object> getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(Map<String, Object> queryParam) {
        this.queryParam = queryParam;
    }
}
