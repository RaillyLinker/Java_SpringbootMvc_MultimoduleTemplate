package com.raillylinker.module_mybatis.mybatis_beans.mybatis1_main.mappers;

import com.raillylinker.module_mybatis.mybatis_beans.mybatis1_main.entities.Mybatis1_Template_TestData;
import com.raillylinker.module_mybatis.mybatis_beans.mybatis1_main.models.FindAllOrderByNearestRandomNumResultVo;
import org.apache.ibatis.annotations.*;

import java.util.List;
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

    @Delete("""
            DELETE 
            FROM 
                template.test_data 
            WHERE 
                uid = #{uid}
            """
    )
    void deleteByUid(
            @Param("uid")
            Long uid
    );

    Optional<Mybatis1_Template_TestData> findByUid(Long uid);

    List<Mybatis1_Template_TestData> findAllWithoutLogicalDeleted();

    List<Mybatis1_Template_TestData> findAllLogicalDeleted();

    List<Mybatis1_Template_TestData> findAll();

    void updateToRowDeleteDateStr(Mybatis1_Template_TestData mybatis1TemplateTestData);

    List<FindAllOrderByNearestRandomNumResultVo> findAllOrderByNearestRandomNum(int num);
}