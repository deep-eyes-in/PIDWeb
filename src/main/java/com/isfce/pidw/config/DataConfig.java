package com.isfce.pidw.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.isfce.pidw.data")
@EnableTransactionManagement
//@EnableSpringDataWebSupport //permet la pagination
public class DataConfig {

	// Si on désire faire les essais avec une base de données mémoire
		@Profile("testU")
		@Bean 
		public DataSource dataSourceTestUnit() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
					//.addScripts("schema.sql", "schemaProf.sql", "datasEtudiant.sql", "datasProfesseur.sql")
					.build();
		}

	// Source de données embarquée avec un pool DBCP
	@Bean
	@Profile("dev")
	public DataSource DataSourceServeur() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl("jdbc:h2:/c:/db/pid1718/pidDB_1718");// Chemin du fichier de la BD
		ds.setUsername("sa");//Valeurs par défaut
		ds.setPassword("");
		ds.setInitialSize(5);
		ds.setMaxTotal(10);
		return ds;
	}

	// Source de données embarquée avec un pool DBCP
	//Nécessite le lancement  du serveur de H2: 
	//          java -cp h2-1.4.196.jar org.h2.tools.Server
		@Bean
		@Profile("pro")
		public DataSource DataSourceTCPServeur() {
			BasicDataSource ds = new BasicDataSource();
			ds.setDriverClassName("org.h2.Driver");
			ds.setUrl("jdbc:h2:tcp://localhost/c:/db/pid1718/pidDB_1718");// Chemin du fichier de la BD
			ds.setUsername("sa");//Valeurs par défaut
			ds.setPassword("");
			ds.setInitialSize(5);
			ds.setMaxTotal(10);
			return ds;

		}
		
	// Gestionnaire de transaction pour JPA
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

	/**
	 * Choix de la persistance Hibernate avec H2 comme base de données
	 * 
	 * @return
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.H2);
		adapter.setShowSql(true); // visualisation des requêtes
		adapter.setGenerateDdl(true);// génération, mise à jour automatique du
										// DDL de la BD
		adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
		return adapter;
	}

	/**
	 * Création d'un entity Manager JPA-Hibernate-H2
	 * 
	 * @param dataSource
	 * @param jpaVendorAdapter
	 * @return un entityManager
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
			JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource);
		emfb.setJpaVendorAdapter(jpaVendorAdapter);
		emfb.setPackagesToScan("com/isfce/pidw/model");// Package pour la
														// découvertes des classes
														// Entités
		return emfb;
	}

	// Permet de transformer les exceptions générées par JPA en Exception Spring
	@Bean
	public BeanPostProcessor persistenceTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
