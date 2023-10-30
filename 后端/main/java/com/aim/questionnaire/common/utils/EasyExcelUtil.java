package com.aim.questionnaire.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class EasyExcelUtil extends AbstractColumnWidthStyleStrategy {
    private static ThreadLocal<Map<String, Integer>> CACHE = ThreadLocal.withInitial(() -> new HashMap<>());


    /**
     * 数据写入excel文件
     *
     * @param keyTitles
     * @param dataLists
     * @param path      记得调用writer.finish()关闭流
     */
    public static void writeFileByTitleArrayEasy(ExcelWriter writer, String[][] keyTitles, List<Map<String, Object>> dataLists, int sheet) {
        List<List<String>> head = new ArrayList<>();
        Arrays.asList(keyTitles).forEach(v -> {
            List<String> a = new ArrayList<>();
            a.add(v[1]);
            head.add(a);
        });
        List<List<Object>> data = new ArrayList<>();
        if (dataLists != null && !dataLists.isEmpty()) {
            for (int k = 0; k < dataLists.size(); k++) {
                Map<String, Object> singleData = dataLists.get(k);
                List<Object> singleDataList = Arrays.asList(keyTitles).stream().map(header -> {
                    if (singleData.get(header[0]) instanceof Date) {
                        return DateUtils.format(((Date) singleData.get(header[0])));
                    }
                    return singleData.get(header[0]);
                }).collect(Collectors.toList());
                data.add(singleDataList);
            }

        }
        WriteSheet writeSheet = EasyExcel.writerSheet(sheet).registerWriteHandler(new EasyExcelUtil()).head(head).build();
        writer.write(data, writeSheet);
    }

    /**
     * 设置自动列宽
     *
     * @param writeSheetHolder
     * @param cellDataList
     * @param cell
     * @param head
     * @param relativeRowIndex
     * @param isHead
     */
    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

        boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
        if (needSetWidth) {
            Map<String, Integer> maxColumnWidthMap = CACHE.get();

            Integer columnWidth = this.dataLength(cellDataList, cell, isHead);
            if (columnWidth >= 0) {
                if (columnWidth > 255) {
                    columnWidth = 255;
                }

                Integer maxColumnWidth = maxColumnWidthMap.get(writeSheetHolder.getSheetNo() + cell.getColumnIndex());
                if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
                    maxColumnWidthMap.put(writeSheetHolder.getSheetNo() + "-" + cell.getColumnIndex(), columnWidth);
                    writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidth * 256);
                }

            }
        }
    }

    /**
     * 获取列宽长度
     *
     * @param cellDataList
     * @param cell
     * @param isHead
     * @return
     */
    private Integer dataLength(List<WriteCellData<?>> cellDataList, Cell cell, Boolean isHead) {
        if (isHead) {
            return cell.getStringCellValue().getBytes().length;
        } else {
            CellData cellData = cellDataList.get(0);
            CellDataTypeEnum type = cellData.getType();
            if (type == null) {
                return -1;
            } else {
                switch (type) {
                    case STRING:
                        return (cellData.getStringValue().getBytes().length + 2) < 30 ? (cellData.getStringValue().getBytes().length < 15 ? 15 : cellData.getStringValue().getBytes().length + 2) : 30;
                    case BOOLEAN:
                        return (cellData.getBooleanValue().toString().getBytes().length + 2) < 30 ? (cellData.getBooleanValue().toString().getBytes().length < 15 ? 15 : cellData.getBooleanValue().toString().getBytes().length + 2) : 30;
                    case NUMBER:
                        return (cellData.getNumberValue().toString().getBytes().length + 2) < 30 ? (cellData.getNumberValue().toString().getBytes().length < 15 ? 15 : cellData.getNumberValue().toString().getBytes().length + 2) : 30;
                    default:
                        return -1;
                }
            }
        }
    }

    public static Map<String, List<Map<String, Object>>> getMultiSheetsDataList(File filepath) {
        Workbook wb = null;
        Sheet sheet;
        Row row;
        String ext = filepath.getName().substring(filepath.getName().lastIndexOf("."));
        try (InputStream is = new FileInputStream(filepath);) {
            if (".xls".equals(ext) || ".xlsx".equals(ext)) {
                wb = WorkbookFactory.create(is);
            } else {
                throw new RuntimeException("不支持格式");
            }
            Map<String, List<Map<String, Object>>> returnMap = new HashMap<>();
            int sheetNum = wb.getNumberOfSheets();
            for (int index = 0; index < sheetNum; index++) {
                List<Map<String, Object>> contentList = new ArrayList<>();
                sheet = wb.getSheetAt(index);
                // 得到总行数
                int rowNum = sheet.getLastRowNum();
                if (rowNum > 0) {
                    // 总列数
                    row = sheet.getRow(0);
                    int colNum = row.getPhysicalNumberOfCells();
                    // 第一行
                    row = sheet.getRow(0);
                    int j = 0;
                    Map<Integer, String> tig = new HashMap<>();
                    while (j < colNum) {
                        Object obj = "";
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            if (cell.getCellType().equals(CellType.FORMULA)) {
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    Date d = cell.getDateCellValue();
                                    obj = DateUtils.format(d);
                                } else {
                                    obj = cell.getNumericCellValue();
                                }
                            } else {
                                obj = cell.getStringCellValue();
                            }
                        }
                        tig.put(j, obj.toString());
                        j++;
                    }

                    DecimalFormat df = new DecimalFormat("#.##");
                    // 正文内容应该从第二行开始,第一行为表头的标题
                    for (int i = 1; i <= rowNum; i++) {
                        row = sheet.getRow(i);
                        j = 0;
                        Map<String, Object> cellValue = new HashMap<>();
                        while (j < colNum) {
                            Object obj = "";
                            Cell cell = row.getCell(j);
                            if (cell != null) {
                                if (cell.getCellType().equals(CellType.FORMULA) || cell.getCellType().equals(CellType.NUMERIC)) {
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        Date d = cell.getDateCellValue();
                                        obj = DateUtils.format(d);
                                    } else {
                                        obj = df.format(cell.getNumericCellValue()).toString();
                                    }
                                } else {
                                    obj = cell.getStringCellValue();
                                }
                            }
                            cellValue.put(tig.get(j), obj);
                            j++;
                        }
                        contentList.add(cellValue);
                    }
                }
                returnMap.put(sheet.getSheetName(), contentList);
            }

            return returnMap;
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }
}
