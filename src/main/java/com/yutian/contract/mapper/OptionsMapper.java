package com.yutian.contract.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OptionsMapper {
    @Select("select id,text,type,parentfield_id from options")
    public List<com.yutian.contract.entity.Options> SelectAll();

    @Select("select * from options where parentfield_id=#{id} order by id")
    public List<com.yutian.contract.entity.Options> GetFieldOptions(@Param("id")int id);
    
    
    @Insert("insert into options (text,parentfield_id,type) values(#{text},#{parentfield_id},#{type})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int AddOption(com.yutian.contract.entity.Options option);

    @Update("update options set text=#{text},type=#{type} where id=#{id}")
    public int UpdateOption(com.yutian.contract.entity.Options option);

    @Delete("delete from options where id=#{id}")
    public int DeleteOption(int id);

    @Delete("delete from options where parentfield_id=#{id}")
    public int DeleteOptionByParentfieldID(int id);
}
