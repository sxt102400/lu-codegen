package com.rats.lu.generator.template;


public class TemplateRenderFactory {

    public static TemplateRender createInstance(String type){
        if("freemarker".equals(type)){
            return new FreemarkerRender();
        }else if("velocity".equals(type)){
            return new VelocityRender();
        }else{
            throw new RuntimeException("with the type["+type+"] cant be init Instance！");
        }
    }

}
