package com.phoenix.unittests;

import com.phoenix.configuration.PersistenceConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;


@ExtendWith(MockitoExtension.class)
public class ProductionDataSourceTest {

    @Mock private Environment env;
    @Mock private Context ctx;
    @InjectMocks private PersistenceConfiguration cfg;
    private final DataSource ds = new DriverManagerDataSource();

    @Test
    void productionDataSource_notBoundInJNDIContext_ShouldThrowBeanCreationException() throws NamingException {
        Mockito.when(env.getProperty("com.phoenix.persistence.datasource.production.jndi-name")).thenReturn("jdbc/phoenix");
        Mockito.when(ctx.lookup("jdbc/phoenix")).thenThrow(NamingException.class);
        Assertions.assertThrows(BeanCreationException.class, () -> this.cfg.productionDataSource(ctx));
    }

    @Test
    void productionDataSource_setInvalidJndiName_ShouldThrowBeanCreationException() throws NamingException {

        Mockito.when(env.getProperty("com.phoenix.persistence.datasource.production.jndi-name")).thenReturn("foo");
        Mockito.when(ctx.lookup(Mockito.anyString())).thenThrow(NamingException.class);
        Assertions.assertThrows(BeanCreationException.class, () -> this.cfg.productionDataSource(ctx));
    }

    @Test
    void productionDataSource_jndiNamePropertyNotFound_ShouldThrowBeanCreationException() throws NamingException {
        Mockito.when(env.getProperty("com.phoenix.persistence.datasource.production.jndi-name")).thenReturn(null);
        Assertions.assertThrows(BeanCreationException.class, () -> this.cfg.productionDataSource(ctx));
    }

    @Test
    void productionDataSource_jndiNamePropertyIsEmpty_ShouldUseDefaultValue() throws NamingException {
        Mockito.when(ctx.lookup("jdbc/phoenix")).thenReturn(new DriverManagerDataSource());
        Mockito.when(env.getProperty("com.phoenix.persistence.datasource.production.jndi-name")).thenReturn("");
        Assertions.assertNotNull(this.cfg.productionDataSource(ctx));
    }

    @Test
    void productionDataSource_setValidJndiNameProperty_shouldReturnDataSourceInstance() throws NamingException {
        Mockito.when(ctx.lookup("jdbc/phoenix")).thenReturn(new DriverManagerDataSource());
        Mockito.when(env.getProperty("com.phoenix.persistence.datasource.production.jndi-name")).thenReturn("jdbc/phoenix");
        Assertions.assertNotNull(this.cfg.productionDataSource(ctx));
    }



}
