package com.rats.lu.generator.config;

import com.rats.lu.generator.exception.GenException;
import com.rats.lu.generator.exception.XMLParserException;
import com.sun.xml.internal.ws.util.xml.XmlUtil;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

public class ConfigurationParser {

    public Configuration parseConfiguration(File inputFile) throws IOException,
            XMLParserException {
        FileReader fr = new FileReader(inputFile);
        return parseConfiguration(fr);
    }

    public Configuration parseConfiguration(Reader reader) throws IOException,
            XMLParserException {
        InputSource is = new InputSource(reader);
        return parseConfiguration(is);
    }

    public Configuration parseConfiguration(InputStream inputStream)
            throws IOException, XMLParserException {
        InputSource is = new InputSource(inputStream);
        return parseConfiguration(is);
    }

    private Configuration parseConfiguration(InputSource inputSource) throws IOException, XMLParserException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new ParserEntityResolver());

            ParserErrorHandler handler = new ParserErrorHandler(warnings, parseErrors);
            builder.setErrorHandler(handler);
            Document document = null;
            try {
                document = builder.parse(inputSource);
            } catch (SAXParseException e) {
                throw new XMLParserException(e.getMessage());
            } catch (SAXException e) {
                if (e.getException() == null) {
                    throw new GenException(e.getMessage());
                } else {
                    throw new GenException(e.getMessage());
                }
            }

            Configuration config;
            Element rootNode = document.getDocumentElement();
            DocumentType docType = document.getDoctype();
            if (rootNode.getNodeType() == Node.ELEMENT_NODE
                    && docType.getPublicId().equals(
                    XmlConstants.MYBATIS_GENERATOR_CONFIG_PUBLIC_ID)) {
                config = parseXmlConfiguration(rootNode);
            } else {
                throw new XMLParserException(getString("RuntimeError.5")); //$NON-NLS-1$
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Configuration parseXmlConfiguration(Element rootNode)
            throws XMLParserException {
        XmlConfigurationParser parser = new XmlConfigurationParser();
        return parser.parse(rootNode);
    }


}
