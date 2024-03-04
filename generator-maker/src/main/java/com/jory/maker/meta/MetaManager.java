package com.jory.maker.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/27 22 50
 * @Description:业务层的工具类 帮我们读取meta.json文件
 *
 */
public class MetaManager {
    //volatile关键字 用来确保多线程环境下的内存可见性 从而保证meta对象一旦改变 其他线程都可看见
    //使用双检索单例模式优化
    private static volatile Meta meta;

    public static Meta getMetaObject(){
        if (meta == null){
            synchronized (MetaManager.class){
                if (meta == null){
                    meta = initMeta();
                }
            }
        }
        return meta;
    }

    public static Meta initMeta(){
        String metaJson = ResourceUtil.readUtf8Str("meta.json");
        Meta newMeta = JSONUtil.toBean(metaJson, Meta.class);
        // 校验配置文件，处理默认值
        MetaValidator.dValidAndFile(newMeta);
        return newMeta;
    }

}
