package com.moying.energyring.database;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;


/**
 * onCreated = "sql"：当第一次创建表需要插入数据时候在此写sql语句
 */
@Table(name = "child")
public class ChildInfo {
    /**
     * name = "id"：数据库表中的一个字段
     * isId = true：是否是主键
     * autoGen = true：是否自动增长
     * property = "NOT NULL"：添加约束
     */
    @Column(name = "id", isId = true, autoGen = true, property = "NOT NULL")
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "time")
    private String time;

    @Column(name = "imgurl")
    private String imgurl;

    public ChildInfo(){

    }
    public ChildInfo(String content, String time, String imgurl){
        this.content = content;
        this.time = time;
        this.imgurl = imgurl;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgurl(){
        return imgurl;
    }

    public void  setImgurl(String imgurl){
        this.imgurl = imgurl;
    }

    @Override
    public String toString() {
        return "ChildInfo{" + "id=" + id + ",content=" + content + ",imgurl" + imgurl   + '}';
    }
}

