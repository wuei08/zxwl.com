package com.zxwl.web.bean.tree;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: zxwl-framework
 * Author: Sendya <18x@loacg.com>
 * Date: 2017/7/18 13:54
 */
public class GoodsClassTree implements Serializable {

    private String id;

    private String classCode;

    @JSONField(name = "text")
    private String className;

    private String parentCode;

    @JSONField(name = "nodes")
    private List<GoodsClassTree> nodes = new ArrayList();

    public GoodsClassTree() {

    }

    public GoodsClassTree(String id, String classCode, String className, String parentCode) {
        this.id = id;
        this.classCode = classCode;
        this.className = className;
        this.parentCode = parentCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public List<GoodsClassTree> getNodes() {
        return nodes;
    }

    public void setNodes(List<GoodsClassTree> nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodsClassTree that = (GoodsClassTree) o;

        if (!id.equals(that.id)) return false;
        if (!classCode.equals(that.classCode)) return false;
        return parentCode != null ? parentCode.equals(that.parentCode) : that.parentCode == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + classCode.hashCode();
        result = 31 * result + (parentCode != null ? parentCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GoodsClassTree{" +
                "id='" + id + '\'' +
                ", classCode='" + classCode + '\'' +
                ", className='" + className + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", nodes=" + nodes +
                '}';
    }
}
