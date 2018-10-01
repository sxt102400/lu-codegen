package com.rats.lu.generator.api;

import com.rats.lu.generator.config.Configuration;
import com.rats.lu.generator.config.JdbcConnectionConfiguration;
import com.rats.lu.generator.config.TableConfiguration;
import com.rats.lu.generator.table.DatabaseIntrospector;
import com.rats.lu.generator.table.IntrospectedTable;
import com.rats.lu.generator.table.JdbcConnectionFactory;
import com.rats.lu.generator.utils.MsgFmt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class LuGenarator {

    private Configuration configuration;
    private JdbcConnectionConfiguration jdbcConnectionConfiguration;
    private JdbcConnectionFactory connectionFactory;
    private List<String> warnings;

    public LuGenarator(Configuration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("configuration cant be null");
        } else {
            this.configuration = configuration;
            jdbcConnectionConfiguration = configuration.getJdbcConnectionConfiguration();
            if (jdbcConnectionConfiguration == null) {
                throw new IllegalArgumentException("jdbcConnectionConfiguration cant be null");
            }
            System.out.println(MsgFmt.getString("[start] 开始连接数据库：{0}", jdbcConnectionConfiguration.getUrl()));
            this.connectionFactory = new JdbcConnectionFactory(this.jdbcConnectionConfiguration);
            if (warnings == null) {
                this.warnings = new ArrayList();
            }
        }
    }

    public void generate() throws SQLException, IOException {
        List<TableConfiguration> tableConfigurations = configuration.getTableConfigurations();
        List<IntrospectedTable> introspectedTables = new ArrayList<>();
        for (TableConfiguration tableConfiguration : tableConfigurations) {
            Connection connection = connectionFactory.getConnection();
            DatabaseIntrospector databaseIntrospector = new DatabaseIntrospector(connection, tableConfiguration);
            IntrospectedTable table = databaseIntrospector.introspectTable();
            if (table != null && table.getClassName()!= null) {
                introspectedTables.add(table);
            }
            connectionFactory.closeConnection(connection);
        }
        for (IntrospectedTable table : introspectedTables) {
            TemplateExecutor templateExecutor = new TemplateExecutor(configuration, table);
            templateExecutor.generate();
        }

        System.out.println(MsgFmt.getString("[success] 文件生成完成！！"));
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
}
