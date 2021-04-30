package com.yutian.contract.mapper;

import java.util.List;

import com.yutian.contract.entity.TextReplacePattern;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TextReplacePatternMapper {

    @Select("select * from textreplacepattern where contract_id=#{id} order by id")
    public List<TextReplacePattern> GetPatternByContractID(@Param("id")int id);
    
   
    @Insert("insert into textreplacepattern (contract_id,name,type) values(#{contract_id},#{name},#{type})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int AddPattern(TextReplacePattern pattern);

    @Update("update textreplacepattern set name=#{name},type=#{type} where id=#{id}")
    public int UpdatePattern(TextReplacePattern pattern);

    @Delete("delete form textreplacepattern where id=#{id}")
    public int DeletePattern(int id);

    @Delete("delete from textreplacepattern where contract_id=#{id}")
    public int DeletePatternByContractID(int id);
}
