package com.diego.lms;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.diego.lms.dao.AuthorDAO;
import com.diego.lms.dao.BookDAO;
import com.diego.lms.dao.PublisherDAO;
import com.diego.lms.service.AdministratorService;
import com.mongodb.MongoClient;

@EnableTransactionManagement
@Configuration
public class LMSConfig {

	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/library";
	private static final String user = "root";
	private static final String pwd = "";

	@Bean
	public AdministratorService adminService() {
		return new AdministratorService();
	}

	@Bean
	public BookDAO bkDao() {
		return new BookDAO();
	}

	@Bean
	public AuthorDAO authDao() {
		return new AuthorDAO();
	}

	@Bean
	public PublisherDAO pubDao() {
		return new PublisherDAO();
	}

	@Bean
	public JdbcTemplate template() {
		JdbcTemplate jdbc = new JdbcTemplate();
		jdbc.setDataSource(datasource());
		
		return jdbc;
	}
	
	@Bean
	public PlatformTransactionManager txManager() {
		DataSourceTransactionManager tx = new DataSourceTransactionManager();
		tx.setDataSource(datasource());
		return tx;
	}

	@Bean
	public BasicDataSource datasource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(pwd);

		return ds;
	}
	
	public @Bean MongoDbFactory getMongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient("localhost",27017), "Library");
    }
 
    public @Bean MongoTemplate getMongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(getMongoDbFactory());
        return mongoTemplate;
    }

}
