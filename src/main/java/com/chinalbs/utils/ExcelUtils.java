package com.chinalbs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.chinalbs.entity.Device;

/**
 * 通过Excel 返回对象
 * 
 * @author shijun
 * 
 */
public class ExcelUtils {

  public static List<Device> processingExcel_XLS(InputStream inputStream) {

    List<Device> devices = new ArrayList<Device>();
    Device device = null;
    HSSFWorkbook hssfWorkbook = null;
    try {
      hssfWorkbook = new HSSFWorkbook(inputStream);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    /** 循环工作表Sheet */
    for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
      HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
      if (hssfSheet == null) {
        continue;
      }
      /** 循环行Row */
      for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
        HSSFRow hssfRow = hssfSheet.getRow(rowNum);
        if (hssfRow == null || rowNum == 0) {
          continue;
        }
        HSSFCell cell = hssfRow.getCell(0);
        if (cell == null) {
          continue;
        }
       
        cell.setCellType(Cell.CELL_TYPE_STRING);
          if("".equals(cell.getStringCellValue())){
            continue;
          
        }
        
        
        /** 实例化device对象 */
        device = new Device();

        /** 循环列Cell */
        for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
          HSSFCell hssfCell = hssfRow.getCell(cellNum);
          if (hssfCell == null || "".equals(hssfCell.getStringCellValue())) {
            continue;
          }
          if (cellNum == 0) {
            hssfCell.setCellType(Cell.CELL_TYPE_STRING);
            device.setfSn(hssfCell.getStringCellValue());
          } else if (cellNum == 1) {
            hssfCell.setCellType(Cell.CELL_TYPE_STRING);
            device.setfPhone(hssfCell.getStringCellValue());
          }
        }
        devices.add(device);
      }
    }
    return devices;
  }

  public static List<Device> processingExcel_XLSX(InputStream inputStream) {

    XSSFWorkbook xssfWorkbook = null;
    List<Device> devices = new ArrayList<Device>();
    Device device = null;

    try {
      xssfWorkbook = new XSSFWorkbook(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }

    /** 循环工作表Sheet */
    for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
      XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
      if (xssfSheet == null) {
        continue;
      }
      /** 循环行Row */
      for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
        XSSFRow xssfRow = xssfSheet.getRow(rowNum);
        if (xssfRow == null || rowNum==0) {
          continue;
        }
        /** 实例化device对象 */
        
        XSSFCell cell = xssfRow.getCell(0);
        if (cell == null) {
          continue;
        }
        cell.setCellType(Cell.CELL_TYPE_STRING);
          if("".equals(cell.getStringCellValue())){
            continue;
        }
        device = new Device();
        /** 循环列Cell */
        for (int cellNum = 0; cellNum <= xssfRow.getLastCellNum(); cellNum++) {
          XSSFCell xssfCell = xssfRow.getCell(cellNum);
          if (xssfCell == null) {
            continue;
          }
          if (cellNum == 0) {
            xssfCell.setCellType(Cell.CELL_TYPE_STRING);
            device.setfSn(xssfCell.getStringCellValue());
          } else if (cellNum == 1) {
            xssfCell.setCellType(Cell.CELL_TYPE_STRING);
            device.setfPhone(xssfCell.getStringCellValue());
          }          
        }
        devices.add(device);
      }
    }
    return devices;
  }
}
