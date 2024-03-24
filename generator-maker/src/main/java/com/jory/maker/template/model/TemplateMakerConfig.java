package com.jory.maker.template.model;

import com.jory.maker.meta.Meta;
import lombok.Data;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/24 23 45
 * @Description:
 */

@Data
public class TemplateMakerConfig {

    /**
     * 模板制作配置
     */
    private Long id;

    private Meta meta = new Meta();

    private String originProjectPath;

    private TemplateMakerFileConfig fileConfig = new TemplateMakerFileConfig();

    private TemplateMakerModelConfig modelConfig = new TemplateMakerModelConfig();

}
