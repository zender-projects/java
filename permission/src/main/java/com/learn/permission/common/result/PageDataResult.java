package com.learn.permission.common.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class PageDataResult<T> {

    private List<T> data = new ArrayList<>();
    private int total = 0;

}
