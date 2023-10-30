package com.aim.questionnaire.common.utils;

import com.aim.questionnaire.entity.QuestionBank;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    public static List<QuestionBank > excelToQuestionBank(MultipartFile file) throws IOException {
        Workbook workbook;
        String filename = file.getOriginalFilename();
        List<QuestionBank> list=new ArrayList<>();
        if (filename.endsWith(".xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());

            // 新建一个list，用于存放解析结果
            try {
                // 拿到对象
                workbook = new HSSFWorkbook(file.getInputStream());
                // 一个excel可能有多个sheet,所以遍历sheet
                for (int i = 0; i <workbook.getNumberOfSheets() ; i++) {
                    // 拿到sheet对象
                    Sheet sheet = workbook.getSheetAt(i);
//                System.out.println("行数"+sheet.getPhysicalNumberOfRows());
                    // 遍历每一行
                    for (int j = 0; j <sheet.getPhysicalNumberOfRows() ; j++) {
                        //略过首行，即标题行
                        if(j==0){
                            continue;
                        }
                        // 拿到行的对象
                        Row row = sheet.getRow(j);
                        // 即使你的表格中没有空行，但也可能会被解析出一些空行，所以我们略过空行，否则会报错
                        if (row==null){
                            continue;
                        }
//                    System.out.println("列数"+row.getPhysicalNumberOfCells());
                        // new一个自己的实体类的对象，方便一会儿将解析出来的值存放进去
                        QuestionBank QuestionBank = new QuestionBank();
                        // 注意下面这种方法是最简单但是也最死板的方法，就是固定excel的表头
                        // 每一列只能是对应的字段。一旦excel的某两列相互替换一下位置
                        // 那么写入QuestionBank的值就乱了

                        // 遍历一行中的每一个单元格
                        for (int k = 0; k <row.getPhysicalNumberOfCells() ; k++) {
                            // 写固定了，每一列内容不能变
                            if(k==0){
                                //订单号
                                QuestionBank.setQuestionId(row.getCell(k).getStringCellValue());
                            }
                            else if(k==1){
                                //产线
                                QuestionBank.setQuestionTitle(row.getCell(k).getStringCellValue());
                            }
                            else if(k==2){
                                // 订单时间
                                QuestionBank.setType(row.getCell(k).getStringCellValue());
                            }
                            else if(k==3){
                                // 交货时间
                                QuestionBank.setStatus(Integer.valueOf(row.getCell(k).getStringCellValue()));
                            }
                            else if(k==4){
                                // 规格
                                QuestionBank.setIsMust(Integer.valueOf(row.getCell(k).getStringCellValue())) ;
                            }
                            else if(k==5){
                                // 备注
                                QuestionBank.setImage(row.getCell(k).getStringCellValue());
                            }
                            else ;
                        }
                        // 将QuestionBank对象添加到list中
                        list.add(QuestionBank);
//                    System.out.println(QuestionBank);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (filename.endsWith(".xlsx")) {

            try {
                workbook = new XSSFWorkbook(file.getInputStream());
                // 一个excel可能有多个sheet,所以遍历sheet
                for (int i = 0; i <workbook.getNumberOfSheets() ; i++) {
                    // 拿到sheet对象
                    Sheet sheet = workbook.getSheetAt(i);
//                System.out.println("行数"+sheet.getPhysicalNumberOfRows());
                    // 遍历每一行
                    for (int j = 0; j <sheet.getPhysicalNumberOfRows() ; j++) {
                        //略过首行，即标题行
                        if(j==0){
                            continue;
                        }
                        // 拿到行的对象
                        Row row = sheet.getRow(j);
                        // 即使你的表格中没有空行，但也可能会被解析出一些空行，所以我们略过空行，否则会报错
                        if (row==null){
                            continue;
                        }
//                    System.out.println("列数"+row.getPhysicalNumberOfCells());
                        // new一个自己的实体类的对象，方便一会儿将解析出来的值存放进去
                        QuestionBank QuestionBank = new QuestionBank();
                        // 注意下面这种方法是最简单但是也最死板的方法，就是固定excel的表头
                        // 每一列只能是对应的字段。一旦excel的某两列相互替换一下位置
                        // 那么写入QuestionBank的值就乱了

                        // 遍历一行中的每一个单元格
                        for (int k = 0; k <row.getPhysicalNumberOfCells() ; k++) {
                            // 写固定了，每一列内容不能变
                            if(k==0){
                                //订单号
                                QuestionBank.setQuestionId(row.getCell(k).getStringCellValue());
                            }
                            else if(k==1){
                                //产线
                                QuestionBank.setQuestionTitle(row.getCell(k).getStringCellValue());
                            }
                            else if(k==2){
                                // 订单时间
                                QuestionBank.setType(row.getCell(k).getStringCellValue());
                            }
                            else if(k==3){
                                // 交货时间
                                QuestionBank.setStatus((int) row.getCell(k).getNumericCellValue());
                            }
                            else if(k==4){
                                // 规格
                                QuestionBank.setIsMust((int) row.getCell(k).getNumericCellValue()) ;
                            }
                            else if(k==5){
                                // 备注
                                QuestionBank.setImage(row.getCell(k).getStringCellValue());
                            }
                            else ;
                        }
                        // 将QuestionBank对象添加到list中
                        list.add(QuestionBank);
//                    System.out.println(QuestionBank);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Unsupported file format");
        }



        return list;
    }
}

