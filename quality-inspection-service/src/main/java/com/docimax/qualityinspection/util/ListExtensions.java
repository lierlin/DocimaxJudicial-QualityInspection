package com.docimax.qualityinspection.util;

import java.util.List;

public class ListExtensions<T> {
    private List<T> list;

    public ListExtensions(List<T> list) {
        this.list = list;
    }

    public boolean isNullOrEmpty() {
        return list == null || list.isEmpty();
    }

    // 这里还可以添加其他的拓展方法
}