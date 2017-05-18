package com.infrastructure.common;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Pattern;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 导出excel工具类
 * Created by suyl on 2016/5/11.
 */
public class ExcelUtils {


    /**
     * 生成Excel
     * @param models 封装需要导出的数据BEAN结合
     * @param className 导成Excel的实体BEAN包名.类名
     * @param excelName 生成的Excel名
     */
    public static void exportExcel(List<List> models, List<String> className,
                                   String excelName, HttpServletResponse response,int rank,int excelModelType,String headTitle)
            throws WriteException, IOException, NoSuchMethodException, ClassNotFoundException {

        OutputStream os = response.getOutputStream();

        WritableWorkbook workbook = Workbook.createWorkbook(os);
        WritableSheet sheet = workbook.createSheet(excelName, 0);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 9,           // 用于标题  样式设置
                WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
                Colour.WHITE);

        WritableFont titleFont1 = new WritableFont(WritableFont.ARIAL, 9,           // 用于标题  样式设置1
                WritableFont.BOLD, false, UnderlineStyle.SINGLE,
                Colour.WHITE);

        WritableFont titleFont3 = new WritableFont(WritableFont.ARIAL, 11,           // 用于标题  样式设置1
                WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
                Colour.WHITE);

        WritableFont titleFont4 = new WritableFont(WritableFont.ARIAL, 11,           // 用于标题  样式设置1
                WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
                Colour.WHITE);

        WritableCellFormat wcf_title = new WritableCellFormat(titleFont);
        wcf_title.setBackground(Colour.TEAL, Pattern.SOLID);
        wcf_title.setBorder(Border.ALL, BorderLineStyle.MEDIUM, Colour.OCEAN_BLUE);
        wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);                   // 垂直对齐
        wcf_title.setAlignment(Alignment.CENTRE);

        WritableCellFormat wcf_title2 = new WritableCellFormat(titleFont);
        wcf_title2.setBackground(Colour.TEAL, Pattern.SOLID);
        wcf_title2.setVerticalAlignment(VerticalAlignment.CENTRE);                   // 垂直对齐
        wcf_title2.setAlignment(Alignment.CENTRE);

        WritableCellFormat wcf_title3 = new WritableCellFormat(titleFont1);
        wcf_title3.setBackground(Colour.TEAL, Pattern.SOLID);
        wcf_title3.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
        wcf_title3.setVerticalAlignment(VerticalAlignment.CENTRE);                   // 垂直对齐
        wcf_title3.setAlignment(Alignment.CENTRE);

        WritableCellFormat wcf_title4 = new WritableCellFormat(titleFont3);
        wcf_title4.setBackground(Colour.TEAL, Pattern.SOLID);
        wcf_title4.setVerticalAlignment(VerticalAlignment.CENTRE);                   //表头样式
        wcf_title4.setAlignment(Alignment.CENTRE);


        WritableFont NormalFont = new WritableFont(WritableFont.TAHOMA, 8);         // 用于正文
        WritableCellFormat wcf_center = new WritableCellFormat(NormalFont);
        wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GRAY_25);
        wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE);                   // 垂直对齐
        wcf_center.setAlignment(Alignment.CENTRE);
        wcf_center.setWrap(true);                                                   // 是否换行


        //进货单详情台头 字样设置
        WritableFont NormalFont1 = new WritableFont(WritableFont.TAHOMA, 20, WritableFont.BOLD);         // 用于正文
        WritableCellFormat wcf_center2 = new WritableCellFormat(NormalFont1);
        wcf_center2.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GRAY_25);
        wcf_center2.setVerticalAlignment(VerticalAlignment.CENTRE);                   // 垂直对齐
        wcf_center2.setAlignment(Alignment.CENTRE);


        WritableFont NormalFont2 = new WritableFont(WritableFont.TAHOMA, 10);         // 用于正文
        WritableCellFormat wcf_center3 = new WritableCellFormat(NormalFont2);
        wcf_center3.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GRAY_25);
        wcf_center3.setVerticalAlignment(VerticalAlignment.CENTRE);                   // 垂直对齐
        wcf_center3.setAlignment(Alignment.CENTRE);
        wcf_center3.setWrap(true);                                                   // 是否换行

        WritableFont NormalFont3 = new WritableFont(WritableFont.TAHOMA, 8);         // 用于正文
        WritableCellFormat wcf_center4 = new WritableCellFormat(NormalFont3);
        wcf_center4.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GRAY_25);
        wcf_center4.setVerticalAlignment(VerticalAlignment.CENTRE);                   // 垂直对齐
        wcf_center4.setAlignment(Alignment.CENTRE);
        wcf_center4.setWrap(true);                                                   // 是否换行

        //标题
        sheet.addCell(new Label(0, 0, excelName, wcf_title2));
        Class classVo = null;
        int cellLength= 0;
        for(int j=0;j<className.size();j++){
            classVo = Class.forName(className.get(0));
            // 获取属性
            Field[] fields = classVo.getDeclaredFields();
            cellLength+=fields.length;
        }
        sheet.mergeCells(0, 0, cellLength-1, 0);//暂时写死

        if(excelModelType == 1){
            makeReceiptDetailExcel(rank,className
                    , sheet, wcf_title4, wcf_center3,wcf_center2,wcf_center4,
                    models,headTitle);
        }else if(excelModelType == 2){
            makeReceiptExcel(rank,className
                    ,sheet,wcf_title,wcf_title2,wcf_center,
                    models);
        }

        workbook.write();
        workbook.close();

        os.flush();
        os.close();
    }

    /**
     *
     * @param rank
     * @param className
     * @param sheet
     * @param wcf_title     表头样式
     * @param wcf_center
     * @param wcf_center2
     * @param models
     * @throws WriteException
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     */
    private static void makeReceiptDetailExcel(int rank, List<String> className
                                               , WritableSheet sheet,
                                               WritableCellFormat wcf_title, WritableCellFormat wcf_center,
                                               WritableCellFormat wcf_center2,WritableCellFormat wcf_center4,
                                               List<List> models,String headTitle) throws WriteException, NoSuchMethodException, ClassNotFoundException {
        Class classVo = null;

        for(int j=0;j<className.size();j++){
            classVo = Class.forName(className.get(j));
            // 获取属性
            Field[] fields = classVo.getDeclaredFields();

            // 获取需要被统计的属性
            Map<Integer,Field> map = new HashMap<>();  //读取需要被统计的属性，位置
            String paramName = "";  //获得统计注释名

            //按照注解id排序Excel列
            Arrays.sort(fields, new FieldComparator());

            int rowId = 1;  //行
            int rowId1 = 1;
            int l = 1;      //列

            if(j==0){
                /** 第一层表头*/
                Label column1=new Label(l, rowId, headTitle, wcf_center2);
                sheet.mergeCells(l, rowId, l + 5, rowId + 2);                 //合并单元格
                sheet.addCell(column1);
            }


            for(int i=0;i<fields.length;i++){
                Field field = fields[i];
                if (field.isAnnotationPresent(ExcelAnnotation.class)) {
                    if (field.isAnnotationPresent(ExcelAnnotation.class)) {
                        //获取该字段的注解对象
                        ExcelAnnotation anno = field
                                .getAnnotation(ExcelAnnotation.class);

                        if(j==0){
                            //第一层表头 //用于同一层级表单左右区分:0 左，1 右 , -1 其他
                            if(anno.rank()==1 && anno.leftOrRight()==1){
                                sheet.setColumnView(l, anno.width());
                                sheet.addCell(new Label(l, rowId + 3, anno.name(), wcf_title));   //获得对应注释中文意思
                                sheet.mergeCells(l, rowId + 3, l + 1, rowId + 3);                 //合并单元格
                                rowId++;
                            }else if(anno.rank()==1 && anno.leftOrRight()==0){
                                if(rowId1==3){
                                    rowId1 = rowId1 +1;
                                }
                                sheet.setColumnView(l+7, anno.width());
                                sheet.addCell(new Label(l + 7, rowId1, anno.name(), wcf_title));        //获得对应注释中文意思
                                sheet.mergeCells(l + 7, rowId1, l + 8, rowId1);                 //合并单元格

                                rowId1++;
                            }
//                            else if(anno.rank()==1 && anno.isStatic() && anno.leftOrRight()==-1){
//                                sheet.setColumnView(9, anno.width());
//                                sheet.addCell(new Label(10, 1, anno.name(), wcf_title));        //获得对应注释中文意思
//
//
//                                sheet.addCell(new Label(10, 2, "", wcf_title));        //获得对应注释中文意思
//                            }
                        }

                        //第二层表头
                        if(anno.rank()==2 && j==1){
                            if(anno.isStatistics()){
                                paramName = anno.name();
                            }else if(anno.isParam()) {
                                map.put(anno.id(), field);
                            }else{
                                sheet.setColumnView(l, anno.width());
                                sheet.addCell(new Label(l, rowId+ 8, anno.name(), wcf_title));        //获得对应注释中文意思
                                if(anno.name().equals("商品名称")||anno.name().equals("商品编码")||anno.name().equals("商品条码")
                                        ||anno.name().equals("特征描述")){
                                    sheet.mergeCells(l,  rowId+ 8, l + 1, rowId+ 8);
                                    l=l+1;
                                }
                                l ++;
                            }
                        }

                    }
                }
            }

            int columnId = 2;   // 写入第4列
            int columnId1 = 0;
            // 获取该类 并获取自身方法
            Object ssTopModel1 = null;
            Class clazz = null;
            if(j==0){
              ssTopModel1 = models.get(j).get(0);
              clazz = ssTopModel1.getClass();

                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    if (field.isAnnotationPresent(ExcelAnnotation.class)
                            && !field.getAnnotation(ExcelAnnotation.class).isStatistics()
                            && !field.getAnnotation(ExcelAnnotation.class).isParam()

                            ) {

                        String methodName = "get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
                        Method method = clazz.getMethod(methodName);

                        try {
                            if(j==0 && field.getAnnotation(ExcelAnnotation.class).rank()==1){
                                if(field.getAnnotation(ExcelAnnotation.class).leftOrRight()==1){
                                    sheet.addCell(new Label(columnId+1, rowId - 1, method
                                            .invoke(ssTopModel1) == null ? ""
                                            : method.invoke(ssTopModel1).toString(), wcf_center4));
                                    sheet.mergeCells(columnId+1, rowId - 1, columnId+4, rowId - 1);                 //合并单元格
                                }else if(field.getAnnotation(ExcelAnnotation.class).leftOrRight()==0){
                                    if(rowId>8){
                                        rowId = rowId-8;
                                    }
                                    sheet.addCell(new Label(columnId1 + 10, rowId == 3 ? ++rowId : rowId, method
                                            .invoke(ssTopModel1) == null ? ""
                                            : method.invoke(ssTopModel1).toString(), wcf_center4));
                                    sheet.mergeCells(columnId1 + 10, rowId == 3 ? ++rowId : rowId, columnId1 + 12, rowId == 3 ? ++rowId : rowId);                 //合并单元格

                                }
                                rowId++;
                            }else if(j==1 && field.getAnnotation(ExcelAnnotation.class).rank()==2){
                                sheet.addCell(new Label(columnId-1, rowId+9, method
                                        .invoke(ssTopModel1) == null ? ""
                                        : method.invoke(ssTopModel1).toString(), wcf_center4));
                            }

                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }else{
                int num = 0;
                for (Object ssTopModel : models.get(j)){
                    clazz = ssTopModel.getClass();

                    if(num!=0){
                        if(!headTitle.equals("商家进货单")){
                            columnId = columnId - 16;
                        }else{
                            columnId = columnId - 12;
                        }

                    }

                    num ++;

                    for (int i = 0; i < fields.length; i++) {
                        Field field = fields[i];
                        if (field.isAnnotationPresent(ExcelAnnotation.class)
                                && !field.getAnnotation(ExcelAnnotation.class).isStatistics()
                                && !field.getAnnotation(ExcelAnnotation.class).isParam()
                                ) {

                            String methodName = "get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
                            Method method = clazz.getMethod(methodName);



                            try {
                                if(j==1 && field.getAnnotation(ExcelAnnotation.class).rank()==2){

                                    if(field.getAnnotation(ExcelAnnotation.class).name().equals("商品名称")
                                            ||field.getAnnotation(ExcelAnnotation.class).name().equals("商品编码")
                                            ||field.getAnnotation(ExcelAnnotation.class).name().equals("商品条码")
                                            ||field.getAnnotation(ExcelAnnotation.class).name().equals("特征描述")){
                                        sheet.mergeCells(columnId - 1, rowId + 9, columnId, rowId + 9);

                                        sheet.addCell(new Label(columnId-1, rowId+9, method
                                                .invoke(ssTopModel) == null ? ""
                                                : method.invoke(ssTopModel).toString(), wcf_center));

                                        columnId = columnId +1;
                                    }else{
                                        sheet.addCell(new Label(columnId-1, rowId+9, method
                                                .invoke(ssTopModel) == null ? ""
                                                : method.invoke(ssTopModel).toString(), wcf_center));
                                    }
                                }

                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            columnId++;
                        }
                    }
                    rowId++;
                }

                if(map.size()>0){
                    rowId = rowId +1;
                    if(headTitle.equals("商家进货单")){
                        sheet.addCell(new Label(columnId-14, rowId+9, paramName, wcf_title));        //获得统计名称
                    }else{
                        sheet.addCell(new Label(columnId-17, rowId+9, paramName, wcf_title));        //获得统计名称
                    }
                    Object ssTopModel = models.get(j).get(0);
                    Set keys = map.keySet();
                    if(keys != null) {
                        Iterator iterator = keys.iterator();
                        while(iterator.hasNext()) {
                            Object key = iterator.next();
                            Field value = map.get(key);

                            if(models.size()>0){
                                Class claszz1 = models.get(j).get(0).getClass();

                                String methodName1 = "get"+value.getName().substring(0,1).toUpperCase()+value.getName().substring(1);
                                Method method1 = claszz1.getMethod(methodName1);    //只取第一条记录 即可

                                try {
                                    if(!headTitle.equals("商家进货单")){
                                        sheet.addCell(new Label(columnId-7, rowId+9, method1
                                                .invoke(ssTopModel) == null ? ""
                                                : method1.invoke(ssTopModel).toString(), wcf_center));
                                    }else{
                                        sheet.addCell(new Label(columnId-2, rowId+9, method1
                                                .invoke(ssTopModel) == null ? ""
                                                : method1.invoke(ssTopModel).toString(), wcf_center));
                                    }

                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }

                                columnId ++;
                            }

                        }
                    }
                }

                //第三层
                if(j==1){
                    Field[] fields1;
                    if(headTitle.equals("商家进货单")){
                        fields1 = Class.forName(className.get(j-1)).getDeclaredFields();
                    }else{
                        fields1 = Class.forName(className.get(j)).getDeclaredFields();
                    }
                    for(int i=0;i<fields1.length;i++){
                        Field field =  fields1[i];
                        if (field.isAnnotationPresent(ExcelAnnotation.class)) {
                            if (field.isAnnotationPresent(ExcelAnnotation.class)) {
                                //获取该字段的注解对象
                                ExcelAnnotation anno = field
                                        .getAnnotation(ExcelAnnotation.class);

                                if(j==1 && anno.rank()==3){
                                    sheet.setColumnView(l, anno.width());
                                    //11,3
                                    sheet.addCell(new Label(l-12, rowId+11, anno.name(), wcf_title));        //获得对应注释中文意思
                                    sheet.addCell(new Label(l - 11, rowId + 11, "", wcf_center4));
                                    l = l+3;
                                }else if(j==1 && anno.rank() == 4){
                                    sheet.setColumnView(l, anno.width());
                                    //11,3
                                    sheet.addCell(new Label(l-24, rowId+12, anno.name(), wcf_title));        //获得对应注释中文意思
                                    sheet.addCell(new Label(l-23, rowId+12, "", wcf_center4));        //获得对应注释中文意思

                                    l = l+3;
                                }

                            }
                        }
                    }
                }

            }



        }
    }

    private static void makeReceiptExcel(int rank, List<String> className
                                            , WritableSheet sheet,
                                         WritableCellFormat wcf_title, WritableCellFormat wcf_title2, WritableCellFormat wcf_center,
                                         List<List> models) throws WriteException, NoSuchMethodException, ClassNotFoundException {

        Class classVo = null;

        classVo = Class.forName(className.get(0));
        // 获取属性
        Field[] fields = classVo.getDeclaredFields();

        // 获取需要被统计的属性
        Map<Integer,Field> map = new HashMap<>();  //读取需要被统计的属性，位置
        String paramName = "";  //获得统计注释名

        //按照注解id排序Excel列
        Arrays.sort(fields, new FieldComparator());

        int rowId = 2;

        //根据最大层级rank数，循环
        for(int k=1;k<=rank;k++){
            //循环输出注释名
            int l = 0;
            rowId = k>1?rowId+2:rowId;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];

                if (field.isAnnotationPresent(ExcelAnnotation.class)) {
                    //获取该字段的注解对象
                    ExcelAnnotation anno = field
                            .getAnnotation(ExcelAnnotation.class);

                    //第一层表头
                    if(anno.rank()==k){
                        if(anno.isStatistics()){
                            paramName = anno.name();
                        }else if(anno.isParam()) {
                            map.put(anno.id(), field);
                        }else{
                            sheet.setColumnView(l, anno.width());
                            sheet.addCell(new Label(l, rowId, anno.name(), wcf_title));        //获得对应注释中文意思
                            l ++;
                        }
                    }
                }
            }

            rowId = rowId>2?rowId:3;// 写入第几行 第一行为列头 数据从第二行开始写

            //读取第一层数据
            if(models.get(0).size()>0){
                if(k==1 && rank >1){
                    int columnId = 0;// 写入第几列 第一列为自动计算的行号 数据从第二列开始写
                    // 获取该类 并获取自身方法
                    Object ssTopModel1 = models.get(0);
                    Class clazz = ssTopModel1.getClass();
                    for (int i = 0; i < fields.length; i++) {
                        Field field = fields[i];
                        if (field.isAnnotationPresent(ExcelAnnotation.class)
                                && !field.getAnnotation(ExcelAnnotation.class).isStatistics()
                                && !field.getAnnotation(ExcelAnnotation.class).isParam()
                                && field.getAnnotation(ExcelAnnotation.class).rank()==k) {

                            String methodName = "get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
                            Method method = clazz.getMethod(methodName);
                            try {

                                sheet.addCell(new Label(columnId, rowId, method
                                        .invoke(ssTopModel1) == null ? ""
                                        : method.invoke(ssTopModel1).toString(), wcf_center));
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            columnId++;
                        }
                    }
                    rowId++;
                }else{
                    List<Object> objects = models.get(0);
                    for (Object ssTopModel : objects) {
                        int mergedCell = 0; //合并单元格数目
                        int columnId = 0;// 写入第几列 第一列为自动计算的行号 数据从第二列开始写

                        // 获取该类 并获取自身方法
                        Class clazz = ssTopModel.getClass();
                        mergedCell = getMergedCell(clazz,ssTopModel,fields,mergedCell);
                        for (int i = 0; i < fields.length; i++) {
                            Field field = fields[i];

                            if (field.isAnnotationPresent(ExcelAnnotation.class)
                                    && !field.getAnnotation(ExcelAnnotation.class).isStatistics()
                                    && !field.getAnnotation(ExcelAnnotation.class).isParam()
                                    && field.getAnnotation(ExcelAnnotation.class).rank()==k) {

                                String methodName = "get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
                                Method method = clazz.getMethod(methodName);
                                try {
                                    if(field.getAnnotation(ExcelAnnotation.class).isMergedCell()
                                            && !field.getAnnotation(ExcelAnnotation.class).isStatistics()
                                            && !field.getAnnotation(ExcelAnnotation.class).isParam()) {
                                        clazz.getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))
                                                .invoke(ssTopModel);
                                        method.invoke(ssTopModel);
                                        //0,3,0,14

                                        Label column1=new Label(columnId, rowId, method
                                                .invoke(ssTopModel) == null ? ""
                                                : method.invoke(ssTopModel).toString(), wcf_center);
                                        sheet.mergeCells(columnId, rowId, columnId, rowId + mergedCell-1);                 //合并单元格
                                        sheet.addCell(column1);
                                    }else{
                                        if(mergedCell != 0){
                                            List<?> listArray = (List<?>) method.invoke(ssTopModel);
                                            int quanitityNum = 0;  //辅助数目
                                            for(int f=0;f<listArray.size();f++){
                                                //5,3  5,4 5,5
                                                sheet.addCell(new Label(columnId, quanitityNum!=0?quanitityNum:rowId, (String) listArray.get(f), wcf_center));
                                                if(quanitityNum==0){
                                                    quanitityNum = rowId;
                                                }
                                                ++quanitityNum;
                                            }
                                        }else {
                                            clazz.getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))
                                                    .invoke(ssTopModel);
                                            method.invoke(ssTopModel);
                                            //0,3,0,14

                                            Label column1=new Label(columnId, rowId, method
                                                    .invoke(ssTopModel) == null ? ""
                                                    : method.invoke(ssTopModel).toString(), wcf_center);
                                            sheet.addCell(column1);
                                        }


                                    }
                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                                columnId++;
                            }
                        }
                        if(mergedCell==0){
                            rowId ++ ;
                        }else{
                            rowId = rowId + mergedCell;
                        }

                    }
                }

            }
        }



        //组装合计行数据
        if(map.size()>0){
            rowId = rowId +1;
            sheet.addCell(new Label(0, rowId, paramName, wcf_title));        //获得统计名称
            Set keys = map.keySet();
            if(keys != null) {
                Iterator iterator = keys.iterator();
                while(iterator.hasNext()) {
                    Object key = iterator.next();
                    Field value = map.get(key);

                    if(models.size()>0 && models.get(0).size()>0){
                        Class claszz = models.get(0).get(0).getClass();

                        String methodName1 = "get"+value.getName().substring(0,1).toUpperCase()+value.getName().substring(1);
                        Method method1 = claszz.getMethod(methodName1);    //只取第一条记录 即可


                        Label labelC = null;
                        try {
                            labelC = new Label((Integer) key-2, rowId, method1
                                    .invoke(models.get(0).get(0)) == null ? ""
                                    : method1.invoke(models.get(0).get(0)).toString(),wcf_center);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        sheet.addCell(labelC);
                    }

                }
            }
        }

    }

    //获取单元格合并数目
    //                                mergeCells(a,b,c,d) 搜索单元格合并函数
    //                                a 单元格的列号
    //                                b 单元格的行号
    //                                c 从单元格[a,b]起，向下合并的列数
    //                                d 从单元格[a,b]起，向下合并的行数
    private static int getMergedCell(Class clazz, Object ssTopModel, Field[] fields, int mergedCell) {
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.getAnnotation(ExcelAnnotation.class).mergedCell() && mergedCell == 0) {  //同一个model只需确认一次
                List<?> listArraty = null;
                try {
                    listArraty = (List<?>) clazz.getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))
                            .invoke(ssTopModel);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return listArraty.size();
            }
        }
        return 0;
    }


    /**
     *
     * @param response
     * @param filename
     * @param models     models 的顺序 应与 className一致
     * @param className
     * @param rank
     * @param excelModelType
     * @throws IOException
     */
    public static void downloadExcel(HttpServletResponse response,
                                      String filename,List<List> models, List<String> className,int rank,int excelModelType,String headTitle) throws IOException {
        //调用上面的方法、生成Excel文件
//        response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/x-msdownload");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(filename.getBytes("utf-8"), "ISO8859-1") + ".xls");

            exportExcel(models, className, filename,response,rank,excelModelType,headTitle);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
