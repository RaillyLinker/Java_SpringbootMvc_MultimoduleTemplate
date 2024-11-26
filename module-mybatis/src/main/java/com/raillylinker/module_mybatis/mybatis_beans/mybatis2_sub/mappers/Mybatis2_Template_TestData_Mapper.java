package com.raillylinker.module_mybatis.mybatis_beans.mybatis2_sub.mappers;

import com.raillylinker.module_mybatis.mybatis_beans.mybatis2_sub.entities.Mybatis2_Template_TestData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface Mybatis2_Template_TestData_Mapper {
    @Select("SELECT * FROM template.test_data WHERE uid = #{uid}")
    Optional<Mybatis2_Template_TestData> findByUid(@Param("uid") Long uid);

    Optional<Mybatis2_Template_TestData> selectTestDataById(Long id);
}