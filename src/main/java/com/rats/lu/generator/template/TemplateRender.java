package com.rats.lu.generator.template;

import java.io.File;
import java.util.Map;

public interface TemplateRender {

    void config(File projectDir, File templateDir);
    void render(Map<String, Object> context, String templateFile, String outputFile, boolean override);
}
