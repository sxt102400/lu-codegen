package com.rats.lu.generator.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.text.MessageFormat;
import java.util.List;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class ParserErrorHandler implements ErrorHandler {

    private List<String> warnings;
    private List<String> errors;

    public ParserErrorHandler(List<String> warnings, List<String> errors) {
        this.warnings = warnings;
        this.errors = errors;
    }

    public void warning(SAXParseException exception) throws SAXException {
        String waring = getString("XML Parser Warning on line {0}: {1}", Integer.toString(exception.getLineNumber()), exception.getMessage());
        this.warnings.add(waring);
    }

    public void error(SAXParseException exception) throws SAXException {
        String error = getString("XML Parser Error on line {0}: {1}", Integer.toString(exception.getLineNumber()), exception.getMessage());
        this.warnings.add(error);
    }

    public void fatalError(SAXParseException exception) throws SAXException {
        String error = getString("XML Parser Error on line {0}: {1}", Integer.toString(exception.getLineNumber()), exception.getMessage());
        this.errors.add(error);
    }

    private static String getString(String text, String... parms) {
        return MessageFormat.format(text, parms);

    }
}