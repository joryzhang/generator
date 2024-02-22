package com.jory.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/21 23 43
 * @Description:静态文件生成器
 */
public class StaticGenerator {

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");//获取当前项目的根目录
        System.out.println(projectPath);
        //
        String inputPath = projectPath + File.separator+"generator-demo-projects"+ File.separator+"acm-template";//separator可以根据操作系统的不通更换不同的
        System.out.println(inputPath);
        String outputPath = projectPath;


//        copyFileByHutool(inputPath,outputPath);
        copyFilesByRecursive(inputPath,outputPath);

    }
    /**
     * 拷贝文件（Hutool实现，会将输入目录完整拷贝到输出目录下）
     * @param inputPath 输入路径
     * @param outputPath 输出路径
     */
    public static void copyFileByHutool(String inputPath, String outputPath){
        FileUtil.copy(inputPath,outputPath,true);
    }

    /**
     * 递归拷贝文件（递归实现，会将输入目录完整拷贝到输出目录下）
     * @param inputPath
     * @param outputPath
     */
    public static void copyFilesByRecursive(String inputPath,String outputPath){
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);
        try {
            copyFileByRecursive(inputFile,outputFile);
        } catch (Exception e) {
            System.err.println("文件复制失败");
            e.printStackTrace();
        }

    }

    public static void copyFileByRecursive(File inputFile, File outputFile) throws IOException {
        //区分是文件还是目录
        if (inputFile.isDirectory()){
            System.out.println(inputFile.getName());
            File destOutputFile = new File(outputFile, inputFile.getName());
            //如果是目录，首先创建目标目录
            if (!destOutputFile.exists()){
                destOutputFile.mkdir();
            }
            //获取目录下的所有文件和子目录
            File[] files = inputFile.listFiles();
            //无子文件，直接结束
            if (ArrayUtil.isEmpty(files)){
                return;
            }
            for (File file : files) {
                //递归拷贝下一层文件
                copyFileByRecursive(file,destOutputFile);
            }
        }else {
            //是文件 直接拷贝到目标目录下
            Path destPath = outputFile.toPath().resolve(inputFile.getName());
            System.out.println(outputFile.toPath());
            System.out.println(inputFile.toPath());
            System.out.println(destPath);
            Files.copy(inputFile.toPath(),destPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }


}
