package com.rats.lu.generator.api;

import com.rats.lu.generator.config.Configuration;
import com.rats.lu.generator.config.ConfigurationParser;
import com.rats.lu.generator.exception.XMLParserException;

import java.io.File;
import java.io.IOException;

public class ShellRunner {


    public static void main(String[] args) {

        String configXML = "/generator.xml";
        File configFile = new File(configXML);
        ConfigurationParser cp = new ConfigurationParser();
        try {
            Configuration config = cp.parseConfiguration(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        }
    }
}
