package es.vir2al.apuestas.fwk.configurations.database;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import es.vir2al.apuestas.app.utils.constants.GeneralAppConstants;

@Configuration
@MapperScan(basePackages = {"es.vir2al."+GeneralAppConstants.APP_NAME+".app.repositories","es.vir2al."+GeneralAppConstants.APP_NAME+".fwk.repositories"}, sqlSessionFactoryRef = "dbSessionFactory")
@EnableTransactionManagement
public class DBDataSourceConfiguration extends DBSessionFactoryConfig{

    @Bean("dbDS")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.db")
    public DataSource createDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="dbTxManager")
    @Primary
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(this.createDataSource());
    }	
	
}