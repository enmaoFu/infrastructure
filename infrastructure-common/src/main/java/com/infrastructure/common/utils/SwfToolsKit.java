package com.infrastructure.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * SWFTools工具类
 *
 * @author tyq
 * @data 2016/1/11
 */
public final class SwfToolsKit {

    public static final String DEFAULT_COMMAND_WINDOWS = " {source} -o {target} -f -T 9";

    public static final String DEFAULT_COMMAND_LINUX = " -s languagedir={font} -T 9 -s poly2bitmap -s zoom=150 -s flashversion=9 \"{source}\" -o \"{target}\"";

    public static final String OS_WINDOWS = "windows";
    public static final String OS_LINUX = "linux";

    private static final Logger logger = LoggerFactory.getLogger(SwfToolsKit.class);

    private static SwfToolsKit instance;

    private String swfToolsHome; // 安装目录
    private String linuxChineseFontDir = "/usr/local/xpdf"; // Linux中文字体目录
    private String os = "windows"; // 操作系统

    /**
     * 转换命令
     *
     * @param source
     * @param target
     * @return
     * @throws IOException
     */
    public static final int pdfToSwf(String source, String target) throws IOException {
        String[] commands =  getCommands(source, target);
        logger.info("转换命令 {}", commands[2]);
        Process pro = null;

        if (OS_WINDOWS.equals(instance.os.toLowerCase()))
            pro = Runtime.getRuntime().exec(commands[2]);
        else
            pro = Runtime.getRuntime().exec(commands);
        //获取文件流
        logger.info("转换信息 {}", loadStream(pro.getInputStream()));
        logger.info("错误信息 {}", loadStream(pro.getErrorStream()));

        try {
            pro.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pro.exitValue();
    }

    /**
     * 获取转换命令
     *
     * @param source
     * @param target
     * @return
     */
    private static final String[] getCommands(String source, String target) {
        String command = null;
        if (OS_WINDOWS.equals(instance.os.toLowerCase())) {
            command = instance.swfToolsHome + DEFAULT_COMMAND_WINDOWS;
            return new String[]{"cmd.exe", "/C", command.replace("{source}", source).replace("{target}", target)};
        } else if (OS_LINUX.equals(instance.os.toLowerCase())) {
            command = instance.swfToolsHome + DEFAULT_COMMAND_LINUX;
            return new String[]{"/bin/sh", "-c", command.replace("{font}", instance.linuxChineseFontDir).replace("{source}", source).replace("{target}", target)};
        }
        throw new IllegalArgumentException("未知的操作系统");
    }

    private static final String loadStream(InputStream in) throws IOException {
        int ptr = 0;
        in = new BufferedInputStream(in);
        StringBuffer buffer = new StringBuffer();

        while ((ptr = in.read()) != -1) {
            buffer.append((char) ptr);
        }

        return buffer.toString();
    }

    public void init() {
        instance = this;
        instance.swfToolsHome = this.swfToolsHome;
        instance.linuxChineseFontDir = this.linuxChineseFontDir;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public void setSwfToolsHome(String swfToolsHome) {
        this.swfToolsHome = swfToolsHome;
    }

    public void setLinuxChineseFontDir(String linuxChineseFontDir) {
        this.linuxChineseFontDir = linuxChineseFontDir;
    }

}
