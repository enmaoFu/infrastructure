package com.infrastructure.spring.poi.excel.util;

import com.infrastructure.spring.poi.excel.OfficeVersion;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

/**
 * 工作簿创建工具类
 *
 * @author Archx
 * @date 2015/7/2 0002
 */
public abstract class WorkbookCreator {

    /**
     * 创建工作簿
     *
     * @param in      文件流
     * @param version Office版本
     * @return 工作簿
     * @throws IOException
     */
    public static Workbook create(InputStream in, OfficeVersion version) throws IOException {
        if (OfficeVersion.EXCEL_2003 == version)
            return new HSSFWorkbook(in);
        else if (OfficeVersion.EXCEL_2007 == version || OfficeVersion.EXCEL_2010 == version)
            return new XSSFWorkbook(in);
        else
            return new HSSFWorkbook();
    }
}
