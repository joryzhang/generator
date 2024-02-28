package com.jory.maker.generator;

import cn.hutool.core.io.FileUtil;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/29 03 22
 * @Description:
 */
public class ScriptGenerator {
    //用StringBuilder 字符串拼接写脚本文件生成器

    /**
     *
     * @param outputPath 指定脚本文件生成的路径
     * @param jarPath  指定Jar包的路径，因为你不知道生成Jar包的名字 只能外部读取
     */
    public static void doGenerate(String outputPath,String jarPath){
        //Linux脚本
        StringBuilder sb = new StringBuilder();
        sb.append("#!/bin/bash").append("\n");
        sb.append(String.format("java -jar %s \"$@\"",jarPath)).append("\n");
        FileUtil.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8),outputPath);
        //Linux下默认是不会有可执行权限
        //添加可执行权限
        Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");
        try {
            Files.setPosixFilePermissions(Paths.get(outputPath),permissions);
        } catch (Exception e) {

        }


        //Windows脚本
        // @echo off
        // java -jar target/generator-basics-1.0-SNAPSHOT-jar-with-dependencies.jar %*
        StringBuilder win = new StringBuilder();
        win.append("@echo off").append("\n");
        win.append(String.format("java -jar %s %%*",jarPath)).append("\n");
        FileUtil.writeBytes(win.toString().getBytes(StandardCharsets.UTF_8),outputPath+".bat");

    }
}
