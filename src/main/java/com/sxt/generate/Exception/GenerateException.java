package com.sxt.generate.Exception;

import java.util.ArrayList;
import java.util.List;
/**
 * ClassName : GenerateException
 * Description : GenerateException
 *
 * @version : v1.1
 * @author : hanbing
 * @since : 2015/5/29
 */
public class GenerateException  extends Exception {

    private List<String> errors;

    public GenerateException(List<String> errors) {
        this.errors = errors;
    }

    public GenerateException(String error) {
        super(error);
        this.errors = new ArrayList();
        this.errors.add(error);
    }

    public List<String> getErrors() {
        return this.errors;
    }
}