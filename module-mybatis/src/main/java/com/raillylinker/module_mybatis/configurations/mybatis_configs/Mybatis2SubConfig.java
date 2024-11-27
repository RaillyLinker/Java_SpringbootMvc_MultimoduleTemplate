package com.raillylinker.module_mybatis.configurations.mybatis_configs;

import com.raillylinker.module_mybatis.const_objects.ModuleConst;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = ModuleConst.PACKAGE_NAME + ".mybatis_beans." + Mybatis2SubConfig.MYBATIS_MAPPERS_NAME + ".mappers",
        sqlSessionFactoryRef = Mybatis2SubConfig.MYBATIS_SQL_SESSION_FACTORY_BEAN_NAME
)
@EnableTransactionManagement
public class Mybatis2SubConfig {
    // !!!application.yml 의 mybatis 안에 작성된 이름 할당하기!!!
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String MYBATIS_CONFIG_NAME = "mybatis2-sub";

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String MYBATIS_MAPPERS_NAME = "mybatis2_sub";

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String TRANSACTION_NAME = MYBATIS_CONFIG_NAME + "_TransactionManager";

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String MYBATIS_DATASOURCE_BEAN_NAME = MYBATIS_CONFIG_NAME + "_DataSource";

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String MYBATIS_SQL_SESSION_FACTORY_BEAN_NAME = MYBATIS_CONFIG_NAME + "_SqlSessionFactory";

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String MYBATIS_SQL_SESSION_TEMPLATE_BEAN_NAME = MYBATIS_CONFIG_NAME + "_SqlSessionTemplate";


    // ----
    @Bean(name = MYBATIS_DATASOURCE_BEAN_NAME)
    @ConfigurationProperties(prefix = "datasource-mybatis." + MYBATIS_CONFIG_NAME)
    public DataSource firstDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = MYBATIS_SQL_SESSION_FACTORY_BEAN_NAME)
    public SqlSessionFactory firstSqlSessionFactory(
            @Qualifier(MYBATIS_DATASOURCE_BEAN_NAME) DataSource dataSource,
            ApplicationContext applicationContext
    ) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-mapper/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis-mapper/mapper/**/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = MYBATIS_SQL_SESSION_TEMPLATE_BEAN_NAME)
    public SqlSessionTemplate firstSqlSessionTemplate(
            @Qualifier(MYBATIS_SQL_SESSION_FACTORY_BEAN_NAME) SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = TRANSACTION_NAME)
    public PlatformTransactionManager transactionManager(
            @Qualifier(MYBATIS_DATASOURCE_BEAN_NAME) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}