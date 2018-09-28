package com.rats.lu.generator.template;

import java.io.File;
import java.util.Map;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public interface TemplateRender {

    void config(File projectDir, File templateDir);
    void render(Map<String, Object> context, String templateFile, String outputFile, boolean override);
}
