package com.jory.maker.template;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.jory.maker.meta.Meta;
import com.jory.maker.template.model.TemplateMakerConfig;
import com.jory.maker.template.model.TemplateMakerFileConfig;
import com.jory.maker.template.model.TemplateMakerModelConfig;
import org.junit.Test;
import java.io.File;
import java.util.Arrays;
import java.util.List;


/**
 * @Author: Jory Zhang
 * @Date: 2024/3/24 18 44
 * @Description:
 */
public class TemplateMakerTest {

    @Test
    public void testMakeTemplateBug1() {
        Meta newMeta = new Meta();
        newMeta.setName("acm-template-generator");
        newMeta.setDescription("ACM 示例模板生成器");
        String projectPath = System.getProperty("user.dir");
        //工作空间隔离
        String originFileRootPath = new File(projectPath).getParent() + File.separator + "generator-demo-projects/springboot-init";
        originFileRootPath.replace("\\", "/");
        String fileInputPath1 = "src/main/java/com/jory/springbootinit/common";

        //文件过滤配置
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig1 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig1.setPath(fileInputPath1);
        List<TemplateMakerFileConfig.FileInfoConfig> templateMakerFileList = Arrays.asList(fileInfoConfig1);
        TemplateMakerFileConfig templateMakerFileConfig = new TemplateMakerFileConfig();
        templateMakerFileConfig.setFiles(templateMakerFileList);


        //模型配置
        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();
        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig1 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig1.setFileName("url");
        modelInfoConfig1.setType("String");
        modelInfoConfig1.setDefaultValue("jdbc:mysql://localhost:3306/my_db");
        modelInfoConfig1.setReplaceText("jdbc:mysql://localhost:3306/my_db");


        List<TemplateMakerModelConfig.ModelInfoConfig> modelInfoConfigList = Arrays.asList(modelInfoConfig1);
        templateMakerModelConfig.setModels(modelInfoConfigList);

        TemplateMaker.makeTemplate(newMeta, originFileRootPath, templateMakerFileConfig, templateMakerModelConfig, 1771152772506865664L);
    }
    @Test
    public void testMakeTemplateBug2() {
        Meta newMeta = new Meta();
        newMeta.setName("acm-template-generator");
        newMeta.setDescription("ACM 示例模板生成器");
        String projectPath = System.getProperty("user.dir");
        //工作空间隔离
        String originFileRootPath = new File(projectPath).getParent() + File.separator + "generator-demo-projects/springboot-init";
        originFileRootPath.replace("\\", "/");
        String fileInputPath1 = "src/main/java/com/jory/springbootinit/common";

        //文件过滤配置
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig1 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig1.setPath(fileInputPath1);
        List<TemplateMakerFileConfig.FileInfoConfig> templateMakerFileList = Arrays.asList(fileInfoConfig1);
        TemplateMakerFileConfig templateMakerFileConfig = new TemplateMakerFileConfig();
        templateMakerFileConfig.setFiles(templateMakerFileList);


        //模型配置
        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();
        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig1 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig1.setFileName("className");
        modelInfoConfig1.setType("String");
        modelInfoConfig1.setReplaceText("BaseResponse");


        List<TemplateMakerModelConfig.ModelInfoConfig> modelInfoConfigList = Arrays.asList(modelInfoConfig1);
        templateMakerModelConfig.setModels(modelInfoConfigList);

        TemplateMaker.makeTemplate(newMeta, originFileRootPath, templateMakerFileConfig, templateMakerModelConfig, 1771152772506865664L);
    }

    @Test
    public void testMakeTemplateWithJSON(){
        String configStr = ResourceUtil.readUtf8Str("templateMaker.json");
        TemplateMakerConfig templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        long id = TemplateMaker.makeTemplate(templateMakerConfig);
        System.out.println(id);
    }

}