package com.phoenix.configuration;

import com.phoenix.exceptions.FileCorruptException;
import javax.naming.Context;
import javax.persistence.ValidationMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration class used to configure application persistence frameworks.
 * Class annotated with {@link Configuration} annotation and imported in {@link RootContextConfiguration} class.
 * Class has a all {@link DataSource} beans definition. Based on active persistence profile application will create only one configured {@link DataSource}
 * JPA {@link LocalContainerEntityManagerFactoryBean} uses this DataSource to exchange all data with database. In this application JPA use Hibernate as
 * its provider.
 */
@Configuration
@PropertySource(value = "classpath:/configuration-files/persistence.properties")
@EnableTransactionManagement
public class PersistenceConfiguration {

    //LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceConfiguration.class);
    //Spring beans
    private Environment env; //autowired

    /**
     * Constructor used by Spring to automatically set {@link Environment} bean.
     * {@link Environment} reads configuration properties from "persistence.properties" under
     * "resources/configuration-files/" directory. Env used as beans that contains all configuration properties for application persistence.
     * @param a_env - Autowired Spring {@link Environment}.
     */
    @Autowired
    public PersistenceConfiguration(Environment a_env) {
        LOGGER.info("Start to initialize " +getClass().getName() +" configuration class");
        LOGGER.info("Start to loading persistence configuration properties from \"persistence.properties\" file.");
        this.env = a_env; //Spring autowiring
    }

    /**
     * Development {@link DataSource} bean has an implementation of {@link DriverManagerDataSource} class.
     * Bean get configuration properties from {@link PersistenceConfiguration#env} bean
     * which initialized in this class constructor {@link PersistenceConfiguration#PersistenceConfiguration(Environment)}.
     * Development DataSource used only for development parameters because is so slow for production use.
     * @return - Configured {@link DataSource} bean.
     */
    @Bean("developmentDataSource")
    @Description("DataSource for DEVELOPMENT profile")
    @Profile("DEVELOPMENT")
    public DataSource developmentDataSource() throws BeanCreationException {

        LOGGER.debug("Create DEVELOPMENT DataSource bean");
        DriverManagerDataSource ds = new DriverManagerDataSource();

        //Set parameters to DataSource
        //Set database url
        LOGGER.debug("Set configuration parameters to DataSource");
        try {
            String database_url = env.getProperty("com.phoenix.persistence.datasource.development.database_url");
            if (database_url == null ) throw new FileCorruptException("persistence.properties");
            if (database_url.isEmpty()) throw  new IllegalArgumentException("Property \"com.phoenix.persistence.datasource.development.database_url\" is not set.");
            ds.setUrl(database_url);
            LOGGER.debug("DataSource URL parameter is installed to: " +database_url +" value");
        }catch (FileCorruptException exc) {
            LOGGER.error(exc.toString());
            LOGGER.error("Please, replace corrupted file with new persistence.properties file.");
            throw new BeanCreationException("Property \"com.phoenix.persistence.datasource.development.database_url\" is not set.");
        }catch (IllegalArgumentException exc) {
            LOGGER.error(exc.toString());
            LOGGER.error("Please. set \"com.phoenix.persistence.datasource.development.database_url\" in persistence.properties file.");
            throw new BeanCreationException("Property \"com.phoenix.persistence.datasource.development.database_url\" is not set.");
        }

        //Set driver class name
        String driver_class_name = env.getProperty("com.phoenix.persistence.datasource.development.driver_class");
        if (driver_class_name == null || driver_class_name.isEmpty()) {
            LOGGER.warn("Property \"com.phoenix.persistence.datasource.development.driver_class\" in \"persistence.properties\" file is not set. "
                    +"Application will use DEFAULT MySQL \"com.mysql.cj.jdbc.Driver\" driver.");
            driver_class_name = "com.mysql.cj.jdbc.Driver";
        }
        ds.setDriverClassName(driver_class_name);
        LOGGER.debug("DataSource driverClassName parameter is installed to: " +driver_class_name +" value");



        //Set user name
        String user_name = env.getProperty("com.phoenix.persistence.datasource.development.user_name");
        if (user_name == null || user_name.isEmpty()) {
            LOGGER.warn("Property \"com.phoenix.persistence.datasource.development.user_name\" in \"persistence.properties\" file is not set. "
                    +"Application will use DEFAULT \"root\" account.");
            user_name = "root";
        }
        ds.setUsername(user_name);
        LOGGER.debug("DataSource Username parameter is installed to: " +user_name +" value");


        //Set password
        String user_password = env.getProperty("com.phoenix.persistence.datasource.development.user_password");
        if (user_password == null || user_password.isEmpty()) {
            LOGGER.warn("Property \"com.phoenix.persistence.datasource.development.user_password\" in \"persistence.properties\" file is not set. "
                    +"Application will use DEFAULT empty password account.");
            user_password = "";
        }
        ds.setPassword(user_password);
        LOGGER.debug("DataSource user_password parameter is installed in empty value");

        LOGGER.debug("Configuring development DataSource is created");
        return ds;
    }

    /**
     * Production {@link DataSource} bean. Application server create a DataSource controlled by him.
     * Production DataSource create a connection pool because this DataSource most powerful than {@link PersistenceConfiguration#developmentDataSource()}.
     * @param jndi_context - {@link Context} bean, used to lookup in JNDI context.
     * @return - Configured {@link DataSource} bean.
     * @throws BeanCreationException - If property "com.phoenix.persistence.datasource.production.jndi-name" is not found,
     * not set or set in invalid value.
     */
    @Bean(value = "productionDataSource")
    @Description("DataSource for PRODUCTION profile")
    @Profile("PRODUCTION")
    @Autowired
    public DataSource productionDataSource(Context jndi_context) throws BeanCreationException {
        LOGGER.info("Start to create PRODUCTION DataSource bean");
        DataSource ds;
        String jndi_name = this.env.getProperty("com.phoenix.persistence.datasource.production.jndi-name");
        try {
            if (jndi_name == null) throw new FileCorruptException("persistence.properties");
            if (jndi_name.isEmpty()) {
                jndi_name = "jdbc/phoenix";
                LOGGER.warn("Property \"com.phoenix.persistence.datasource.production.jndi-name\" is not set. Application will use DEFAULT " +jndi_name +" value." );
            }
            ds = (DataSource) jndi_context.lookup(jndi_name);
        }catch (FileCorruptException exc) {
            LOGGER.error(exc.toString());
            throw new BeanCreationException("Property \"com.phoenix.persistence.datasource.production.jndi-name\" is not set.", exc);
        }catch (NamingException exc) {
            LOGGER.error(exc.toString(), exc);
            throw new BeanCreationException("DataSource with JNDI name: " +jndi_name +" was not found to JNDI context.", exc);
        }
        return ds;
    }

    /**
     * {@link LocalContainerEntityManagerFactoryBean} bean is the factory for {@link javax.persistence.EntityManager} beans.
     * EntityManagerFactoryBean use active DataSource, scanning {@link com.phoenix.models} package on persistence units (entities)
     * and used Hibernate as default JPa provider.
     * @param ds - Active {@link DataSource} bean.
     * @return - Configured {@link LocalContainerEntityManagerFactoryBean} bean.
     */
    @Bean
    @Description("JPA Entity manager factory")
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {

        LOGGER.info("Create " +LocalContainerEntityManagerFactoryBean.class.getName() +" persistence bean.");
        final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        //Set DataSource
        LOGGER.debug("Set " +DataSource.class.getName() +" to " +LocalContainerEntityManagerFactoryBean.class.getName());
        emf.setDataSource(ds);

        //Implements JPA Vendor adapter interface
        LOGGER.debug("Create " +HibernateJpaVendorAdapter.class.getName() +" object.");
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        LOGGER.debug("Set " +JpaVendorAdapter.class.getName() +" to " +LocalContainerEntityManagerFactoryBean.class.getName());
        emf.setJpaVendorAdapter(adapter);

        //Set persistence units scans
        LOGGER.debug(LocalContainerEntityManagerFactoryBean.class.getName() +": Set packages to scan.");
        emf.setPackagesToScan("com.phoenix.models");

        //Set additional hibernate properties
        LOGGER.debug(LocalContainerEntityManagerFactoryBean.class.getName() +": Load additional Hibernate configuration properties.");
        emf.setJpaProperties(this.loadHibernateProperties());

        //Disable hibernate entity validation
        LOGGER.debug(LocalContainerEntityManagerFactoryBean.class.getName() +": Disable entity validation mode.");
        emf.setValidationMode(ValidationMode.NONE);

        LOGGER.debug(LocalContainerEntityManagerFactoryBean.class.getName() +" was created.");
        return emf;
    }

    /**
     * {@link PlatformTransactionManager} bean used to handle database transactions.
     * @param emf - {@link EntityManagerFactory} factory that are created
     * in {@link PersistenceConfiguration#entityManagerFactory(DataSource)} bean.
     * @return - {@link JpaTransactionManager} bean.
     */
    @Bean
    @Description("Transaction manager for single entity manager factory")
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        LOGGER.info("Create " +PlatformTransactionManager.class.getName() +" persistence bean.");

        LOGGER.debug(PlatformTransactionManager.class.getName() +" is implemented by " +JpaTransactionManager.class.getName());
        final JpaTransactionManager tm = new JpaTransactionManager();
        LOGGER.debug("Set " +EntityManagerFactory.class.getName() +" to " +PlatformTransactionManager.class.getName() +" bean.");
        tm.setEntityManagerFactory(emf);

        LOGGER.debug(PlatformTransactionManager.class.getName() +" was created.");
        return tm;
    }

    /**
     * Method load configuration properties from "persistence.properties" file.
     * These properties start with "com.phoenix.persistence.hibernate.*" key.
     * All these properties has a default values.
     * @return - Configuration {@link Properties} object.
     */
    private Properties loadHibernateProperties() {
        //Create properties object
        Properties props = new Properties();

        LOGGER.info("Load Hibernate configuration properties from \"persistence,properties\" file");

        //Set Hibernate dialect property
        String dialect = this.env.getProperty("com.phoenix.persistence.hibernate.dialect");
        if (dialect == null || dialect.isEmpty()) {
            LOGGER.warn("Property \"com.phoenix.persistence.hibernate.dialect\" is not set. Application will use default MySQL 8 dialect.");
            dialect = "org.hibernate.dialect.MySQL8Dialect";
        }
        LOGGER.debug("Hibernate dialect property: " +dialect);
        props.setProperty("hibernate.dialect", dialect);

        //Set Hibernate show_sql property
        String show_sql = this.env.getProperty("com.phoenix.persistence.hibernate.show_sql");
        if (show_sql == null || show_sql.isEmpty()) {
            LOGGER.warn("Property \"com.phoenix.persistence.hibernate.show_sql\" is not set. Application will use default false value.");
            show_sql = "false";
        }
        LOGGER.debug("Hibernate show_sql property: " +show_sql);
        props.setProperty("hibernate.show_sql", show_sql);

        //Set Hibernate generate statistic property
        String gen_stat = this.env.getProperty("com.phoenix.persistence.hibernate.generate_statistics");
        if (gen_stat  == null || gen_stat .isEmpty()) {
            LOGGER.warn("Property \"com.phoenix.persistence.hibernate.generate_statistics\" is not set. Application will use default true value.");
            gen_stat  = "true";
        }
        LOGGER.debug("Hibernate generate_statistics property: " +gen_stat);
        props.setProperty("hibernate.generate_statistics", gen_stat );

        //Set Hibernate use_sql_comments property
        String sql_comm = this.env.getProperty("com.phoenix.persistence.hibernate.use_sql_comments");
        if (sql_comm  == null || sql_comm .isEmpty()) {
            LOGGER.warn("Property \"com.phoenix.persistence.hibernate.use_sql_comments\" is not set. Application will use default true value.");
            sql_comm  = "true";
        }
        LOGGER.debug("Hibernate use_sql_comments property: " +sql_comm);
        props.setProperty("hibernate.use_sql_comments", sql_comm);

        //Set Hibernate hbm2dll.auto property
        String hbm2dll = this.env.getProperty("com.phoenix.persistence.hibernate.hbm2ddl.auto");
        if (hbm2dll == null || hbm2dll .isEmpty()) {
            LOGGER.warn("Property \"com.phoenix.persistence.hibernate.hbm2ddl.auto\" is not set. Application will use default update value.");
            hbm2dll  = "update";
        }
        LOGGER.debug("Hibernate hbm2ddl.auto property: " +hbm2dll);
        props.setProperty("hibernate.hbm2ddl.auto", hbm2dll);

        props.setProperty("hibernate.default_entity_mode", "pojo");
        LOGGER.info("All hibernate configuration properties are loaded");

        return props;
    }

}
