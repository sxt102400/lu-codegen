package com.rats.lu.generator.template;

import com.rats.lu.generator.utils.StringTools;
import com.rats.lu.generator.utils.TemplateUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

public class VelocityRender implements TemplateRender {


    @Override
    public void config(File projectDir, File templateDir) {

    }

    public void render(Map<String, Object> context, String templateFileName, String outputFileName, boolean override) {
        BufferedWriter writer = null;
        try {
            File outfile = new File(outputFileName);
            if (outfile.exists()) {
                System.out.println("   [!跳过] 类文件已经存在:" + outputFileName);
                return;
            }
            File dir = outfile.getParentFile();
            if (!dir.exists()) dir.mkdirs();

            InputStream proIn = Thread.currentThread().getContextClassLoader().getResourceAsStream("velocity.properties");
            URL classesURL = Thread.currentThread().getContextClassLoader().getResource("");
            String classesPath = new File(classesURL.toURI()).getAbsolutePath();

            Properties prop = new Properties();
            prop.load(proIn);
            prop.put("file.resource.loader.path", classesPath);
            Velocity.init(prop);
            VelocityContext contextMap = new VelocityContext();
            for (Map.Entry<String, Object> entry : context.entrySet()) {
                contextMap.put(entry.getKey(), entry.getValue());
            }
            contextMap.put("StringTools", new StringTools());
            contextMap.put("MyDateTool", new TemplateUtils.MyDateTool());
            org.apache.velocity.Template template = Velocity.getTemplate(templateFileName);

            System.out.println("   [生成文件] 生成类文件:" + outputFileName);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile), "UTF-8"));
            template.merge(contextMap, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (Exception e) {

                e.printStackTrace();
            }
        }

    }
}
