package ru.kck.usb.zags.fataloiv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.sdc.config.IConnectionsManager;

import javax.sql.DataSource;

/**
 * @author Ilya V. Kupriyanov
 * @version 08/02/2020
 */

@Configuration
public class PostgresConfiguration {

    private static final String ALIAS = "MRDFNSBroadcast";

    @Autowired
    private IConnectionsManager connectionsManager;

    @Bean(name = "deathDataSource")
    public DataSource getDataSource() {
        return connectionsManager.getConfiguration(ALIAS).getDataSource();
    }

    @Bean(name = "deathTemplate")
    public JdbcTemplate getJdbcTemplate(){return connectionsManager.getConfiguration(ALIAS).getJdbcTemplate();}

    @Bean(name = "deathOperations")
    public NamedParameterJdbcOperations getJdbcOperations() {
        return connectionsManager.getConfiguration(ALIAS).getJdbcOperations();
    }

    @Bean(name = "deathSchema")
    public String getSchema() {return connectionsManager.getConfiguration(ALIAS).getSchema();}

}
