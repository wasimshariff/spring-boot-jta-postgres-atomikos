package com.example.postgres;

import com.atomikos.icatch.config.UserTransactionServiceImp;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.example.postgres.aspect.LoggingAspect;
import com.example.postgres.model.TestingProperties;
import mytest.sharedaspect.AspectDemoApplication;
import org.aspectj.lang.Aspects;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.UserTransaction;

@SpringBootApplication
@EnableConfigurationProperties(TestingProperties.class)
@EnableAspectJAutoProxy
@EnableAsync
@PropertySource("classpath:app-application.properties")
@Import({AspectDemoApplication.class}) //this works ( but u need to provide @Bean for aspect in AspectDemoApplication config)
//  or ComponentScan also works ( this doesnt need @Bean annotation)
//@ComponentScan({"com.example.postgres","mytest.sharedaspect"})
//@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
public class PostgresApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostgresApplication.class, args);
	}

/*
	Below is not needed if @Configuration on Aspect
	@Bean
	public LoggingAspect getLoggingAspect() {
		return new LoggingAspect();
	}*/
/*	@Bean
	public AspectDemoApplication getAspectDemoApplication() {
		return new AspectDemoApplication();
	}*/




	/*@Bean
	public LoggingAspect loggingAspect() {
		LoggingAspect aspect = Aspects.aspectOf(LoggingAspect.class);
		return aspect;
	}*/

	@Bean
	@DependsOn("userTransactionServiceImp")
	public UserTransaction userTransaction()  {
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		return userTransactionImp;
	}

	@Bean(initMethod = "init" , destroyMethod = "shutdownWait")
	public UserTransactionServiceImp userTransactionServiceImp() throws Throwable {
		UserTransactionServiceImp userTransactionImp = new UserTransactionServiceImp();
		return userTransactionImp;
	}

	@Bean
	@DependsOn("userTransactionServiceImp")
	public UserTransactionManager userTransactionManager() {
		UserTransactionManager userTransactionImp = new UserTransactionManager();
		return userTransactionImp;
	}

	@Bean
	public JtaTransactionManager jtaTransactionManager() {
		JtaTransactionManager userTransactionManager = new JtaTransactionManager();
		userTransactionManager.setUserTransaction(userTransaction());
		userTransactionManager.setTransactionManager(userTransactionManager());
		return userTransactionManager;
	}

	// Not sure if this is needed
/*	@Bean
	public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
		return new PersistenceAnnotationBeanPostProcessor();
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}*/

}
