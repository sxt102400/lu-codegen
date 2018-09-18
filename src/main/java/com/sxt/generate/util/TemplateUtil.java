package com.sxt.generate.util;

import freemarker.template.Configuration;
import org.apache.commons.lang3.StringUtils;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * ClassName : TemplateUtil
 * Description : TemplateUtil模版操作类
 *
 * @version : v1.1
 * @author : hanbing
 * @since : 2015/5/29
 */
public class TemplateUtil {

    public static void renderVelocity(Map<String,Object> contextMap, String temp, String output) {
        BufferedWriter writer = null;
        try {
            File outfile = new File(output);
            if (outfile.exists()) {
                System.out.println("   [!跳过] 类文件已经存在:" + output);
                return;
            }
            File dir = outfile.getParentFile();
            if (!dir.exists()) dir.mkdirs();

            InputStream proIn =  Thread.currentThread().getContextClassLoader().getResourceAsStream("velocity.properties");
            URL classesURL = Thread.currentThread().getContextClassLoader().getResource("");
            String classesPath = new File(classesURL.toURI()).getAbsolutePath();

            Properties prop = new Properties();
            prop.load(proIn);
            prop.put("file.resource.loader.path",classesPath );
            Velocity.init(prop);
            VelocityContext context = new VelocityContext();
            for(Map.Entry<String,Object> entry : contextMap.entrySet()){
                context.put(entry.getKey(), entry.getValue());
            }
            context.put("StringUtils", new StringUtils());
            context.put("MyDateTool", new MyDateTool());
            org.apache.velocity.Template template = Velocity.getTemplate(temp);

            System.out.println("   [生成文件] 生成类文件:" + output);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile),"UTF-8"));
            template.merge(context, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)   writer.close();
            } catch (Exception e) {

                e.printStackTrace();
            }
        }

    }

    public static void renderFreemarker(Map<String,Object> contextMap, String temp, String output,boolean override) {
        BufferedWriter writer = null;
        try {
            File outfile = new File(output);
            String overrideInfo = " [生成文件]";
            if( outfile.exists() ) {
                if(!override){
                    System.out.println("   [!跳过] 类文件已经存在:" + output);
                    return;
                }else{
                    overrideInfo = " [生成文件并覆盖]";
                }
            }

            File dir = outfile.getParentFile();
            if (!dir.exists()) dir.mkdirs();

            InputStream proIn =  Thread.currentThread().getContextClassLoader().getResourceAsStream("freemarker.properties");
            URL classesURL = Thread.currentThread().getContextClassLoader().getResource("");
            String classesPath = new File(classesURL.toURI()).getAbsolutePath();

            Properties prop = new Properties();
            prop.load(proIn);
            //prop.put("file.resource.loader.path",classesPath );

            Configuration   cfg = new Configuration(Configuration.VERSION_2_3_22);
            cfg.setSettings(prop);
            cfg.setDirectoryForTemplateLoading(new File(classesPath));
            freemarker.template.Template template = cfg.getTemplate(temp);

            System.out.println( " >>> " + overrideInfo + " : " + output);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile),"UTF-8"));
            template.process(contextMap, writer);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)   writer.close();
            } catch (Exception e) {

                e.printStackTrace();
            }
        }

    }
    /**
     * @ClassName: Configuration
     * @Description:Configuration
     * @version:v1.0.0
     * @author: haning
     * @date:
     */
    public static class StringTool {

        /*
       * _单词转为驼峰单词
       */
        public static String toCamel(String text) {

            StringBuilder textarr = new StringBuilder(text.toLowerCase());
            for (int i = 0; i < textarr.length() - 1; i++) {
                if (textarr.charAt(i) == '_') {
                    int j = i + 1;
                    if (j < textarr.length() && textarr.charAt(j) >= 'a' && textarr.charAt(j) <= 'z') {
                        int charInt = (int) textarr.charAt(j) - 32;
                        textarr.setCharAt(j, (char) charInt);
                    }

                }
            }
            return textarr.toString().replaceAll("_", "");
        }
    }

    /**
     * @ClassName: Configuration
     * @Description:Configuration
     * @version:v1.0.0
     * @author: haning
     * @date:
     */
    public static class MyDateTool {
        public String now() {
            return DateFormat.getDateTimeInstance().format(new Date());
        }
    }
}
