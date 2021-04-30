package com.yutian.contract.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.command.BaseModel;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

@Table(name = "Options")
public class Options{
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public int  getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getParentfield_id() {
        return parentfield_id;
    }
    public void setParentfield_id(int parentfield_id) {
        this.parentfield_id = parentfield_id;
    }
    @Column(name="id",type=MySqlTypeConstant.INT,length = 11,isKey = true,isAutoIncrement = true)
    private int id;
    @Column(name="text",type=MySqlTypeConstant.VARCHAR,length = 100)
    private String text;
    @Column(name="type",type=MySqlTypeConstant.INT)
    private int type;
    @Column(name="parentfield_id",type = MySqlTypeConstant.INT,length = 11,isKey = false)
    private int parentfield_id;
}
