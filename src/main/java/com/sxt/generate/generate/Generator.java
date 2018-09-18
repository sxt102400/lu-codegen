package com.sxt.generate.generate;

import com.sxt.generate.config.Configuration;
import com.sxt.generate.config.ConfigurationParser;
import com.sxt.generate.config.GenerateConfiguration;
import com.sxt.generate.table.Table;
import com.sxt.generate.table.TableFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * Generator
 * Description:Generator生成器类
 *
 * @version: v1.1
 * @author: hanbing
 * @since : 2015/5/29
 */
public class Generator {

    public static void generate() {
        Configuration configuration = new ConfigurationParser().parse("/generator.xml");

        List<Table> tables = getTableInfos(configuration);
        for (Table table : tables) {
            generateFile(configuration,table);
        }
        System.out.println("生成文件结束!!!!!");
        //generateFile(getTableInfos());
    }

    public static void generateFile(Configuration configuration,Table table) {
        TemplateExecutor tempEx  = new TemplateExecutor().configure(configuration);
        tempEx.renderTemplate(table);
    }

    public static List<Table> getTableInfos(Configuration configuration) {

        String driver =configuration.getJdbcConfiguration().getDriverClassName();
        String url = configuration.getJdbcConfiguration().getUrl();
        String username = configuration.getJdbcConfiguration().getUsername();
        String password = configuration.getJdbcConfiguration().getPassword();
        assert StringUtils.isNotBlank(driver) : "driver can't read";
        assert StringUtils.isNotBlank(url) : "url can't read";

        List<GenerateConfiguration> generates = configuration.getGenerateConfigurations();
        List<Table> tables = new ArrayList<Table>();

        for (GenerateConfiguration generate : generates) {

            String tableName = generate.getTableName();
            String className = generate.getClassName();
            assert StringUtils.isNotBlank(tableName) : "tableName can't read";
            assert StringUtils.isNotBlank(className) : "className can't read";

            TableFactory tableFactory =  TableFactory.getInstance(driver, url, username, password);
            Table table = new Table();
            table.setTableName(tableName);
            table.setClassName(className);
            table = tableFactory.introspectTable(table);
            if(table!=null) {
                tables.add(table);
            }
        }
        return tables;

    }
}
