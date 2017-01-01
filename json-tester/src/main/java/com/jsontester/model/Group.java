package com.jsontester.model;

/**
 * Group
 * Created by SCWANG on 2016/5/23.
 */
public class Group {

    private String id;
    private String desc;
    private String name;

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
