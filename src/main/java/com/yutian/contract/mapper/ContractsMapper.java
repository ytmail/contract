package com.yutian.contract.mapper;


import java.util.List;

import com.yutian.contract.entity.Contracts;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;


@Mapper
public interface ContractsMapper {
    
    @Select("select id,name,creator,createdate from contracts order by id desc")
    public List<Contracts> SelectAll(RowBounds rowBounds);

    @Select("select id,name,creator,createdate from contracts where id=#{id}")
    public Contracts GetContract(int id);
    

    @Select("select template from contracts where id=#{id}")
    public Contracts GetContractTemplate(@Param("id")int id);
    
    
    @Insert("insert into contracts (name,creator,createdate,template) values(#{name},#{creator},#{createdate},#{template})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int AddContract(Contracts contract);

    @Update("update contracts set name=#{name},createdate=#{createdate},template=#{template} where id=#{id}")
    public int UpdateContract(Contracts contract);

    @Delete("delete from contracts where id=#{id}")
    public int DeleteContract(int id);

}
