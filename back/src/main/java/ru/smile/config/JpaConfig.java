package ru.smile.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = {"ru.smile.repositories"})
@EnableTransactionManagement
public class JpaConfig {

//  @Bean
//  public DataSource dataSource() {
//
//    BasicDataSource dataSourceConfig = new BasicDataSource();
//    dataSourceConfig.setDriverClassName("org.postgresql.Driver");
//
//    dataSourceConfig.setUrl("jdbc:postgresql://127.0.0.1:5432/databasename");
//    dataSourceConfig.setUsername("username");
//    dataSourceConfig.setValidationQuery("SELECT 1");
//    dataSourceConfig.setPassword("password");
//
//    return dataSourceConfig;
//
////    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
////    return builder.setType(EmbeddedDatabaseType.HSQL).build();
//  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl("jdbc:postgresql://localhost:5432/app");
    dataSource.setUsername("postgres");
    dataSource.setPassword("postgres");
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setShowSql(true);
    vendorAdapter.setGenerateDdl(true);
    vendorAdapter.setDatabase(Database.POSTGRESQL);
    vendorAdapter.setDatabasePlatform(org.hibernate.dialect.PostgreSQL95Dialect.class.getName());

    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//    LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();
//    factoryBean.setPersistenceUnitName("app");
    entityManagerFactoryBean.setDataSource(dataSource());
    entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
    entityManagerFactoryBean.setPackagesToScan("ru.smile.entities");
    entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
    entityManagerFactoryBean.setJpaProperties(hibProperties());
    entityManagerFactoryBean.setPersistenceUnitName("default");
//    entityManagerFactoryBean.afterPropertiesSet();

    return entityManagerFactoryBean;
  }

  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);

    return transactionManager;
  }

  protected Properties hibProperties()  {
    Properties allproperties = new Properties();
    Properties properties = new Properties();
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    try(InputStream input = classLoader.getResourceAsStream("application.properties")){
      allproperties.load(input);
    } catch (IOException e) {
      e.printStackTrace();
    }

    for( Object p : allproperties.keySet()){
      String s = (String)p;
      if(s.startsWith("hibernate."))
        properties.put(p, allproperties.get(p));
    }

//    properties.put("hibernate.physical_naming_strategy", getNamingStrategyClass().getName()); //стратегия именования таблиц и полей

    return properties;
  }
}
