package com.aaw.aaw.H_tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
@Slf4j
//无混淆命名文件
public class noFixFileSubmit {
    public void submit(MultipartFile file,String proName, String path) throws IOException {
        log.info("文件大小: {}", file.getSize());
        //解析数据
        log.info("文件开始上传");
        String fn = file.getOriginalFilename();
        log.info("文件名称: {}", fn);

        // 生成新的文件名
        String nn =proName+fn;
        // 获取项目根目录
        String projectRoot = System.getProperty("user.dir");
        // 构建保存文件的路径
        String filePath = projectRoot + File.separator + "src" + File.separator + "main" +
                File.separator + "resources" + File.separator + "static" + File.separator + path +
                File.separator + nn;
        // 创建File对象表示目标文件
        File destFile = new File(filePath);
        // 将上传的文件保存到目标文件
        file.transferTo(destFile);
    }
}
