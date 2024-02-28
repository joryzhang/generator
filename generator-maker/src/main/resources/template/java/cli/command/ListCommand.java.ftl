package ${basePackage}.cli.command;

import cn.hutool.core.io.FileUtil;
import picocli.CommandLine;
import picocli.CommandLine;

import java.io.File;
import java.util.List;

/**
 * @Author: ${author}
 * @Date: ${createTime}
 * @Description:用来生成所有的文件信息
 */
@CommandLine.Command(name = "list",mixinStandardHelpOptions = true)
public class ListCommand implements Runnable{



    @Override
    public void run() {
        //获取整个项目根路径
        String projectPath = System.getProperty("user.dir");
        File parentFile = new File(projectPath).getParentFile();
        //输入路径
        String inputPath = new File(parentFile,"acm-template").getAbsolutePath();
        System.out.println(inputPath);
        List<File> files = FileUtil.loopFiles(inputPath);
        for (File file : files) {
            System.out.println(file);
        }

    }

}
