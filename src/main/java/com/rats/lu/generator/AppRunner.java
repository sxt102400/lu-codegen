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

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class AppRunner {

    public static void main(String[] args) {

        try {
            String configFile= "generator.xml";
            InputStream in  = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
            ConfigurationParser cp = new ConfigurationParser();
            Configuration configuration = cp.parse(in);
            LuGenarator generator = new LuGenarator(configuration);
            generator.generate();
        } catch (XMLParserException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
