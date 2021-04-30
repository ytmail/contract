package com.yutian.contract.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.command.BaseModel;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import java.util.Date;
import java.util.List;

@Table(name = "Contracts")
public class Contracts {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public byte[] getTemplate() {
        return template;
    }

    public void setTemplate(byte[] template) {
        this.template = template;
    }

    public List<TextReplacePattern> PatternList;
    @Column(name = "id", type = MySqlTypeConstant.INT, length = 11, isKey = true, isAutoIncrement = true)
    private int id;
    @Column(name = "name", type = MySqlTypeConstant.VARCHAR, length = 50, isKey = false, isAutoIncrement = false)
    private String name;
    @Column(name = "creator", type = MySqlTypeConstant.VARCHAR, length = 50, isKey = false, isAutoIncrement = false)
    private String creator;
    @Column(name = "createdate", type = MySqlTypeConstant.DATETIME)
    private Date createdate;
    @Column(name = "template", type = MySqlTypeConstant.MEDIUMBLOB)
    private byte[] template;

}
