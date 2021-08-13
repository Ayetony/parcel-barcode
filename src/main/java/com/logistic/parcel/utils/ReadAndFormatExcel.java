package com.logistic.parcel.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.LinkedList;

public class ReadAndFormatExcel {

    static String PICTURE_PATH = System.getProperty("user.dir") + "/barcode.png";

    public static void format(String proPath) {
        try (InputStream inp = new FileInputStream(proPath)) {
            XSSFWorkbook wb = (XSSFWorkbook) XSSFWorkbookFactory.create(inp);
            LinkedList<String> linkedList = new LinkedList<>();
            XSSFSheet sheet = wb.getSheetAt(0);
            sheet.getColumnHelper().setColWidth(23,43);
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 1; i < rows ; i++) {
                XSSFRow row = sheet.getRow(i);
                row.setHeight((short) (320*7));
                XSSFCell cell = row.getCell(1);
                String msg = cell.getStringCellValue().trim();
                linkedList.add(i-1,msg);
            }
            try (OutputStream fileOut = new FileOutputStream(proPath)) {
                wb.write(fileOut);
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            draw(proPath, PICTURE_PATH,linkedList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void  draw(String proPath,String picPath,LinkedList<String> linkedList) {
        XSSFWorkbook wb = null;
        XSSFSheet sheet = null;
        try (InputStream inp = new FileInputStream(proPath)) {
            wb = (XSSFWorkbook) XSSFWorkbookFactory.create(inp);
            sheet = wb.getSheetAt(0);
        }catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < linkedList.size(); i++) {

            String msg = linkedList.get(i);
            BarcodeUtils.generateFile(msg, picPath);
            //add picture data to this workbook.
            InputStream fileInputStream;
            byte[] bytes;
            int pictureIdx = 0;
            try {
                fileInputStream = new FileInputStream(picPath);
                bytes  = IOUtils.toByteArray(fileInputStream);
                pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CreationHelper helper = wb.getCreationHelper();
            Drawing<XSSFShape> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(23);
            anchor.setRow1(i+1);
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            pict.resize(1.0,0.9);
        }
        //save workbook
        try (OutputStream fileOut = new FileOutputStream(proPath)) {
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
