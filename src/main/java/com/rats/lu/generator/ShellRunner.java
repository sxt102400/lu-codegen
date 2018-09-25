package com.rats.lu.generator;

import com.rats.lu.generator.api.ConfigurationParser;
import com.rats.lu.generator.api.LuGenarator;
import com.rats.lu.generator.config.Configuration;
import com.rats.lu.generator.exception.XMLParserException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShellRunner {

    public static void main(String[] args) {
        String configFile= "generator.xml";
        List<String> warnings = new ArrayList<String>();
        InputStream in  = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
        ConfigurationParser cp = new ConfigurationParser();
        try {
            Configuration configuration = cp.parse(in);
            LuGenarator genarator = new LuGenarator(configuration,warnings);
            genarator.generate();
        } catch (XMLParserException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
