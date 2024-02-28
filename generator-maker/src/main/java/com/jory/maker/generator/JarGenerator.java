package com.jory.maker.generator;

import java.io.*;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/29 00 47
 * @Description:
 */
public class JarGenerator {
    public static void doGenerate(String projectDir) throws IOException, InterruptedException {
        //调用 Process 类执行Maven打包命令
        String otherMavenCommand = "mvn clean package -DskipTest=true";//先清除再打包 并且跳过测试 快速进行打包
        String winMavenCommand = "mvn.cmd clean package -DskipTest=true";//先清除再打包 并且跳过测试 快速进行打包
        String mavenCommand = winMavenCommand;

        //创建
        ProcessBuilder processBuilder = new ProcessBuilder(mavenCommand.split(" "));//这里要拆分字符串 否则会当作一整个字符串
        processBuilder.directory(new File(projectDir));//执行命令路径

        Process process = processBuilder.start();//执行命令

        //读取命令的输出
        InputStream inputStream = process.getInputStream();//获取信息流
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            System.out.println(line);
        }

            int exitCode = process.waitFor();
        System.out.println("命令执行结束: 退出码:" + exitCode);
    }

}
