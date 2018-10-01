package com.rats.lu.generator.template;


import com.rats.lu.generator.utils.MsgFmt;
import freemarker.template.Configuration;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * Copyright (C) 2016
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class FreemarkerRender implements TemplateRender {

    private File projectDir;
    private File templateDir;
    private Configuration cfg;

    public void config(File projectDir, File templateDir) {
        this.projectDir = projectDir;
        this.templateDir = templateDir;
        try {
            Properties prop = new Properties();
            prop.put("whitespace_stripping", true);
            prop.put("template_update_delay", 1);
            prop.put("locale", "zh_CN");
            prop.put("default_encoding", "utf-8");
            cfg = new Configuration(Configuration.VERSION_2_3_28);
            cfg.setDirectoryForTemplateLoading(this.templateDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render(Map<String, Object> context, String templateFileName, String outputFileName, boolean override) {
        BufferedWriter writer = null;
        try {
            String overrideInfo = "";
            File outFile = new File(projectDir.getCanonicalPath(), outputFileName);
            if (outFile.exists() && !override) {
                System.out.println(MsgFmt.getString("\t\t[skip！] 类文件已经存在:{0}", outFile.getCanonicalPath()));
                return;
            }
            freemarker.template.Template template = cfg.getTemplate(templateFileName);

            File dir = outFile.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!outFile.exists()) {
                outFile.createNewFile();
            } else {
                overrideInfo = "was override";
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
            template.process(context, writer);
            System.out.println(MsgFmt.getString("[file] 生成文件成功: {0}  {1}", outFile.getCanonicalPath(), overrideInfo));

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
