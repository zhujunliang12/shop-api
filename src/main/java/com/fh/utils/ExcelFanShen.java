package com.fh.utils;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelFanShen {

    public static void main(String [] args) throws Exception {
  //      List list = excelFanShen("d:/sss.xls", User.class, new String[]{"userName", "password","userAge","phone","errorCount", "errorDate"});
        System.out.println();
    }

    public static Map<String, Method> getMethod(Class cla){
        HashMap<String, Method> map = new HashMap<>( );
        Method[] declaredMethods = cla.getDeclaredMethods( );
        for (Method method:declaredMethods) {
            map.put(method.getName().toUpperCase(),method);
        }
        return map;
    }



    public static List excelFanshe(String file,Class cla,String names[]) throws Exception {
        String zhui = file.substring(file.lastIndexOf("."));
        Workbook workbook=null;
        InputStream in=null;
        //拿到待反射中的所有方法
        Map<String, Method> method = ExcelFanShen.getMethod(cla);
        try {
            in=new FileInputStream(file);
            if(zhui.equals(".xls")){
                workbook=new HSSFWorkbook(in);
            }else if(zhui.equals(".xlsx")){
                workbook=new XSSFWorkbook(in);
            }else{
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace( );
        }finally {
            try {
                if(in!=null){  in.close();}
            } catch (IOException e) {
                e.printStackTrace( );
            }
        }
        Object o = cla.newInstance( );
        List list=new ArrayList();
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum( );
        for(int i=1;i<lastRowNum+1;i++){
            Row row=sheet.getRow(i);
            int physicalNumberOfCells = row.getPhysicalNumberOfCells( );
            for (int j=0;j<physicalNumberOfCells;j++){
                Cell cell = row.getCell(j);
                CellType cellTypeEnum = cell.getCellTypeEnum();
                Object param=null;
                switch (cellTypeEnum){
                    case STRING:
                        param = cell.getStringCellValue( );
                        break;
                    case NUMERIC:
                        if(DateUtil.isCellDateFormatted(cell)){
                            param = cell.getDateCellValue( );
                        }else{
                            param = cell.getNumericCellValue( );
                        }
                        break;
                    case _NONE:
                        cell=null;
                        break;
                        default:
                }
                String name = names[j];
                String mapName="SET"+name.toUpperCase();
                Method method1 = method.get(mapName);
                Class [] parameterTypes = method1.getParameterTypes();
                for (Class c:parameterTypes) {
                    if(c==Integer.class){
                        param = new Double(param.toString()).intValue();
                    }
                }
                method1.invoke(o,param);

            }
            list.add(o);
        }
        return list;
    }






    public static List excelFanShen(String fileName,Class cla,String [] params) throws InstantiationException, IllegalAccessException {

        String zhui=fileName.substring(fileName.lastIndexOf("."));
        Workbook workbook=null;
        InputStream in=null;
        try {
            in=new FileInputStream(fileName);
            if(zhui.equals(".xls")) {
                workbook=new HSSFWorkbook(in);
            }else {
                workbook=new XSSFWorkbook(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List list=new ArrayList();
        for(Sheet sheet:workbook) {
            int lastRowNum = sheet.getLastRowNum();
            for(int i=1;i<lastRowNum+1;i++) {
                Object o = cla.newInstance();
                Row row = sheet.getRow(i);
                int Cells = row.getPhysicalNumberOfCells();
                for(int j=0;j<Cells;j++) {
                    Cell cell = row.getCell(j);
                    CellType cellTypeEnum = cell.getCellTypeEnum();
                    Object param=null;
                    switch(cellTypeEnum) {
                        case STRING:
                            param=cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            if(DateUtil.isCellDateFormatted(cell)) {
                                param=cell.getDateCellValue();
                            }else {
                                param=cell.getNumericCellValue();
                            }
                            break;
                        default:
                    }
                    String can = params[j];
                    String mth="SET"+can.toUpperCase();
                    Map map = ExcelFanShen.getMethod(cla);
                    Method method = (Method)map.get(mth);
                    if(method==null) {continue;}
                    Class [] parameterTypes = method.getParameterTypes();
                    for(int b=0;b<parameterTypes.length;b++) {
                        if(parameterTypes[b]==Integer.class) {
                            param=new Double(param.toString()).intValue();
                        }
                    }
                    try {
                        method.invoke(o, param);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                list.add(o);
            }
        }

        return list;

    }

    /**
 /*    *
     * @return
     *//*
    public static Map<String, Method> getMethod(Class cla){
        HashMap<String, Method> map = new HashMap<String, Method>( );
        Method[] declaredMethods = cla.getDeclaredMethods( );
        for (Method method:declaredMethods) {
            map.put(method.getName(),method);
        }
        return map;
    }*/
}
