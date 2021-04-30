package com.yutian.contract.entity;


import java.util.List;


import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

@Table(name = "TextReplacePattern")
public class TextReplacePattern {
    public List<Options> Field_Options;

    
    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

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

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Column(name = "contract_id", type = MySqlTypeConstant.INT, length = 11, isKey = false, isAutoIncrement = false)
    private int contract_id;
    @Column(name = "id", type = MySqlTypeConstant.INT, length = 11, isKey = true, isAutoIncrement = true)
    private int id;
    @Column(name = "name", type = MySqlTypeConstant.VARCHAR, length = 100, isKey = false, isAutoIncrement = false)
    private String name;
    @Column(name = "type", type = MySqlTypeConstant.INT)
    private int type;
}
