package com.sxt.generate.desert.config;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName : SourceConfiguration
 * Description : SourceConfiguration
 *
 * @version: v1.1
 * @author: hanbing
 * @since : 2015/5/29
 */
public class SourceConfig {

    private String folder;

    private String basePackage;

    private List<FileConfig> fileConfigs = new ArrayList<FileConfig>();


    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public List<FileConfig> getFileConfigs() {
        return fileConfigs;
    }

    public void setFileConfigs(List<FileConfig> fileConfigs) {
        this.fileConfigs = fileConfigs;
    }
}
