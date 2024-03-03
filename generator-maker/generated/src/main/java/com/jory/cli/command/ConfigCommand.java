package com.jory.cli.command;

import cn.hutool.core.util.ReflectUtil;
import com.jory.model.DataModel;
import picocli.CommandLine;

import java.lang.reflect.Field;

/**
 * @Author: jory
 * @Date: 2024-02-27
 * @Description:配置辅助命令 作用是允许用户传入的动态参数信息 也就是MainTemplateConfig类的字段信息
 */
@CommandLine.Command(name = "config", mixinStandardHelpOptions = true)
public class ConfigCommand implements Runnable {
    @Override
    public void run() {
        //利用反射机制来拿到类的所有信息
        //这里使用hutool工具.ReflectUtil拿到所有字段信息
        Field[] fields = ReflectUtil.getFields(DataModel.class);
        for (Field field : fields) {
            System.out.println("字段类型" + field.getType());
            System.out.println("字段名称" + field.getName());
        }

    }

}
