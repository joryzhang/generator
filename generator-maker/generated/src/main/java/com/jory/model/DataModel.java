package com.jory.model;

import lombok.Data;

/**
 * @Author: jory
 * @Date: 2024-02-27
 * @Description:ACM示例模板生成器
 */
@Data
public class DataModel {


        /**
        * 是否生成循环
        */
    private boolean loop = false;


        /**
        * 作者注释
        */
    private String author = "jory";


        /**
        * 输出信息
        */
    private String outputText = "sum = ";




}
