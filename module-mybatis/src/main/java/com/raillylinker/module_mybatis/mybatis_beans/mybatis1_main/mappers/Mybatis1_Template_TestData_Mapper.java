package com.raillylinker.module_mybatis.mybatis_beans.mybatis1_main.mappers;

import com.raillylinker.module_mybatis.mybatis_beans.mybatis1_main.entities.Mybatis1_Template_TestData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface Mybatis1_Template_TestData_Mapper {
    @Insert("""
            INSERT 
            INTO 
                template.test_data 
                (
                 uid, 
                 row_create_date, 
                 row_update_date, 
                 row_delete_date_str, 
                 content, 
                 random_num, 
                 test_datetime
                 ) 
            VALUES 
                (
                 #{uid}, 
                 #{rowCreateDate}, 
                 #{rowUpdateDate}, 
                 #{rowDeleteDateStr}, 
                 #{content}, 
                 #{randomNum}, 
                 #{testDatetime}
                 )
            """
    )
    void save(Mybatis1_Template_TestData testData);

    @Select("""
            SELECT 
                * 
            FROM 
                template.test_data 
            WHERE 
                uid = #{uid}
            """
    )
    Optional<Mybatis1_Template_TestData> findByUid(@Param("uid") Long uid);

    Optional<Mybatis1_Template_TestData> selectTestDataById(Long id);
}