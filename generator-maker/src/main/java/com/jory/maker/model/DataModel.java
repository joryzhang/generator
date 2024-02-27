package com.jory.maker.model;

import lombok.Data;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/22 15 56
 * @Description:静态模板配置
 */
@Data
public class DataModel {
    //明确需求
    //1.在代码开头增加author
    //2，修改程序输出的信息
    //3.将循环改为单次读取(可选代码)

    private String author = "jory";


    private String outputText = "sum=";

    /**
     * 是否循环 loop(开关)
     */
    private boolean loop;


}
