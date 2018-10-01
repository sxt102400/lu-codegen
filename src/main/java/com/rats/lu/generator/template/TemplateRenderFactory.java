package com.rats.lu.generator.template;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class TemplateRenderFactory {

    public static TemplateRender createInstance(String type){
        if("freemarker".equals(type)){
            return new FreemarkerRender();
        }else{
            throw new RuntimeException("with the type["+type+"] cant be init Instance！");
        }
    }

}
