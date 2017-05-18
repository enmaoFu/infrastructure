package com.infrastructure.spring.poi.excel.handler;

import com.infrastructure.spring.poi.excel.EntityHandle;
import com.infrastructure.spring.poi.excel.OfficeVersion;
import com.infrastructure.spring.poi.excel.CellRender;
import com.infrastructure.spring.poi.excel.ExcelReader;
import com.infrastructure.spring.poi.excel.render.DefaultCellRender;
import com.infrastructure.spring.poi.excel.util.WorkbookCreator;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * SimpleExcelReader
 *
 * @author tyq
 * @date 2016/1/11
 */
public class SimpleExcelReader<E> extends ExcelReader<E> {

    /**
     * 值渲染器
     */
    private CellRender cellRender = new DefaultCellRender();

    private EntityHandle<E> entityHandle = new EntityHandle<E>() {
        @Override
        public E handle(E e) {
            return e;
        }
    };

    /**
     * 构造函数
     *
     * @param in     文件流
     * @param fields 字段数组
     * @throws IOException
     */
    public SimpleExcelReader(InputStream in, String[] fields) throws IOException {
        this(in, fields, OfficeVersion.EXCEL_2003);
    }

    /**
     * 构造函数
     *
     * @param in      文件流
     * @param fields  字段数组
     * @param version 软件版本
     * @throws IOException
     */
    public SimpleExcelReader(InputStream in, String[] fields, OfficeVersion version) throws IOException {
        this(in, fields, 0, 1, 0, version);
    }

    /**
     * 构造函数
     *
     * @param in         文件流
     * @param fields     字段数组
     * @param sheetIndex 工作表索引
     * @param rowIndex   开始行索引
     * @param cellIndex  开始列索引
     * @param version    软件版本
     * @throws IOException
     */
    public SimpleExcelReader(InputStream in, String[] fields, int sheetIndex, int rowIndex, int cellIndex, OfficeVersion version) throws IOException {
        this(WorkbookCreator.create(in, version), fields, sheetIndex, rowIndex, cellIndex);
    }

    /**
     * 构造函数
     *
     * @param workbook   工作簿
     * @param fields     字段数组
     * @param sheetIndex 工作表索引
     * @param rowIndex   开始行索引
     * @param cellIndex  开始列索引
     */
    public SimpleExcelReader(Workbook workbook, String[] fields, int sheetIndex, int rowIndex, int cellIndex) {
        super(workbook, fields, sheetIndex, rowIndex, cellIndex);
    }

    /**
     * 设置单元格渲染器
     *
     * @param cellRender 渲染器
     * @return this
     */
    public SimpleExcelReader setCellRender(CellRender cellRender) {
        this.cellRender = cellRender;
        return this;
    }

    /**
     * 设置实体处理器
     *
     * @param entityHandle 处理器
     * @return this
     */
    public SimpleExcelReader setEntityHandle(EntityHandle<E> entityHandle) {
        this.entityHandle = entityHandle;
        return this;
    }

    @Override
    protected Object render(Field field, Object value) {
        return cellRender.render(field, value);
    }

    @Override
    protected E handle(E e) {
        return entityHandle.handle(e);
    }
}
