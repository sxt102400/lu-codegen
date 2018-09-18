package com.sxt.generate.desert.util;

import com.sxt.generate.desert.config.ConfigParser;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * ClassName : XmlUtil
 * Description : XmlUtil操作类
 *
 * @version : v1.1
 * @author : hanbing
 * @since : 2015/5/29
 */
public class XmlUtil {

    public static Document readXml(String file) {
        Document root = null;
        SAXReader reader = new SAXReader();
        try {
            root = reader.read(ConfigParser.class.getResourceAsStream(file));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return root;
    }

    public static Node readNode(Document root,String saxPath){
        return  root.selectSingleNode(saxPath);
    }


    public static List<Element> readNodes(Document root,String saxPath){
        return  root.selectNodes(saxPath);
    }

    public static String getPropertyName(Element elemet){
        return  elemet.attribute("name").getValue();
    }

    public static String getPropertyValue(Element elemet){
        return  elemet.attribute("value").getValue();
    }

}
