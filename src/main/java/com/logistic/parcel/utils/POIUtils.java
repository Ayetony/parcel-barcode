package com.logistic.parcel.utils;

import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class POIUtils {
    public static void addPictureToExcel(XSSFWorkbook workbook, ByteArrayOutputStream byteArrayOutputStream, PicturePosition picturePosition) {
        int sheetNumber=workbook.getNumberOfSheets();
        for(int i=0;i<sheetNumber;i++) {
            XSSFSheet sheet=workbook.getSheetAt(i);
            XSSFDrawing drawing=sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor=new XSSFClientAnchor(picturePosition.getDx1(),picturePosition.getDx2(),
                    picturePosition.getDy1(),picturePosition.getDy2(),picturePosition.getCol1(),
                    picturePosition.getRow1(),picturePosition.getCol2(),picturePosition.getRow2());
            drawing.createPicture(anchor, workbook.addPicture(byteArrayOutputStream.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG));
        }
    }

    public static void main(String[] args) {
        String excelFile="src/main/resources/test.xlsx";
        FileOutputStream fileOutputStream = null;
        BufferedImage bufferedImage;
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try {
            bufferedImage= ImageIO.read(new File("src/main/resources/static/pages/images/barcode.png"));
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            File file = new File(excelFile);
            if(!file.exists()){
                file.createNewFile();
            }
            FileInputStream fileInputStream=new FileInputStream(file);
            XSSFWorkbook workbook=new XSSFWorkbook(fileInputStream);
            PicturePosition picturePosition=new PicturePosition(0,0,255,255,(short)1,1,(short)4,7);
            addPictureToExcel(workbook, byteArrayOutputStream, picturePosition);
            fileOutputStream =new FileOutputStream(file);
            workbook.write(fileOutputStream);
            System.out.print(excelFile + "  Success!");
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
