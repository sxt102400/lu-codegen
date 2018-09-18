package com.sxt.generate.desert.generate;

import com.sxt.generate.desert.config.GenConfig;
import com.sxt.generate.desert.config.ConfigParser;
import com.sxt.generate.desert.config.GenerateConfig;
import com.sxt.generate.desert.table.Table;
import com.sxt.generate.desert.table.TableFactory;
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

        String file = "generator.xml.bak";
        GenConfig configuration = new ConfigParser().parse(file);

        List<Table> tables = getTableInfos(configuration);
        for (Table table : tables) {
            generateFile(configuration,table);
        }
        //generateFile(getTableInfos());
    }

    public static void generateFile(GenConfig configuration,Table table) {
        TemplateExecutor tempEx  = new TemplateExecutor().configure(configuration);
        tempEx.renderTemplate(table);
    }

    public static List<Table> getTableInfos(GenConfig configuration) {

        String driver =configuration.getJdbcConfig().getDriverClassName();
        String url = configuration.getJdbcConfig().getUrl();
        String username = configuration.getJdbcConfig().getUsername();
        String password = configuration.getJdbcConfig().getPassword();
        assert StringUtils.isNotBlank(driver) : "driver can't read";
        assert StringUtils.isNotBlank(url) : "url can't read";

        List<GenerateConfig> generates = configuration.getGenerateConfigs();
        List<Table> tables = new ArrayList<Table>();

        for (GenerateConfig generate : generates) {

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
