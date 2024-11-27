package com.raillylinker.module_mybatis;

import com.raillylinker.module_mybatis.mybatis_beans.mybatis1_main.entities.Mybatis1_Template_TestData;
import com.raillylinker.module_mybatis.mybatis_beans.mybatis1_main.mappers.Mybatis1_Template_TestData_Mapper;
import com.raillylinker.module_mybatis.mybatis_beans.mybatis2_sub.entities.Mybatis2_Template_TestData;
import com.raillylinker.module_mybatis.mybatis_beans.mybatis2_sub.mappers.Mybatis2_Template_TestData_Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class ModuleTest {
    @Autowired
    Mybatis1_Template_TestData_Mapper mybatis1TemplateTestDataMapper;

    @Autowired
    Mybatis2_Template_TestData_Mapper mybatis2TemplateTestDataMapper;

    @Test
    void mybatisBasicTest() {
        Optional<Mybatis1_Template_TestData> result2 = mybatis1TemplateTestDataMapper.findByUid(1L);
        if (result2.isPresent()) {
            System.out.println("result2");
            System.out.println(result2.get().randomNum);
            System.out.println();
        } else {
            System.out.println("result2 is null");
            System.out.println();
        }


        Optional<Mybatis2_Template_TestData> result22 = mybatis2TemplateTestDataMapper.selectTestDataById(1L);
        if (result22.isPresent()) {
            System.out.println("result22");
            System.out.println(result22.get().randomNum);
            System.out.println();
        } else {
            System.out.println("result22 is null");
            System.out.println();
        }
    }
}
