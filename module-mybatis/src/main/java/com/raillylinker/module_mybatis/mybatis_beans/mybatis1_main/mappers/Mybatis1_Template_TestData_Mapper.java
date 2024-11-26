package com.raillylinker.module_mybatis.mybatis_beans.mybatis1_main.mappers;

import com.raillylinker.module_mybatis.mybatis_beans.mybatis1_main.entities.Mybatis1_Template_TestData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface Mybatis1_Template_TestData_Mapper {
    @Select("SELECT * FROM template.test_data WHERE uid = #{uid}")
    Optional<Mybatis1_Template_TestData> findByUid(@Param("uid") Long uid);

    Optional<Mybatis1_Template_TestData> selectTestDataById(Long id);
}