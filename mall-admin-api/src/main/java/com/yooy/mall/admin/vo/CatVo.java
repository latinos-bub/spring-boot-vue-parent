package com.yooy.mall.admin.vo;

import java.util.List;

/**
 * @author: util.youl.com@gmail.com
 * @date: 2019/6/17 22:16
 * @description:
 * @version: 1.0
 * @className: CatVo
 */
public class CatVo {

    private Integer value = null;

    private String label = null;

    private List children = null;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }
}
