package com.learn.permission.common.bean;

import lombok.Getter;
import lombok.Setter;

public class PageQuery {

    @Getter
    @Setter
    private Integer pageNo = 1;

    @Getter
    @Setter
    private Integer pageSize = 10;

    @Setter
    private Integer offset;

    public Integer getOffset(){
        return (pageNo - 1) * pageSize;
    }
}
