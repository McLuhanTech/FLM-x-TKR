package com.akd;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "com.akd.mapper", sqlSessionTemplateRef  = "simpleSqlSessionTemplate")
public class DBSimpleConfig {

    @Bean(name = "simpleDataSource")
    @ConfigurationProperties(prefix = "datasource.simple")
    public DataSource simpleDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "simpleSqlSessionFactory")
    public SqlSessionFactory simpleSqlSessionFactory(@Qualifier("simpleDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.akd.entity");
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*Mapper.xml"));
        return bean.getObject();
    }

    @Bean(name = "simpleTransactionManager")
    public DataSourceTransactionManager simpleTransactionManager(@Qualifier("simpleDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "simpleSqlSessionTemplate")
    public SqlSessionTemplate simpleSqlSessionTemplate(@Qualifier("simpleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
