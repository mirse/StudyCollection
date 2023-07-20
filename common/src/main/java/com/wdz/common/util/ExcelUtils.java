package com.wdz.common.util;

import android.util.Log;

import org.apache.log4j.lf5.viewer.LogTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtils {
    private static final String TAG = "ExcelUtils";
    public static void initExcel(String filePath,String fileName,String sheetName,int sheetIndex,String[] title,String[][] content){
        try {
            File file = new File(filePath+"/"+ fileName);
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream os = new FileOutputStream(file);
            WritableWorkbook workbook = Workbook.createWorkbook(os);
            WritableSheet sheet;
            int numberOfSheets = workbook.getNumberOfSheets();
            if (numberOfSheets != 0 && workbook.getSheet(sheetIndex) != null){
                sheet = workbook.getSheet(sheetIndex);
            }
            else{
                sheet = workbook.createSheet(sheetName, sheetIndex);
            }
            Label label;

            //合并单元格
            sheet.mergeCells(0,0,1,0);
            for (int i = 0; i < title.length; i++) {
                //label = new Label(i, 0, title[i]);
                label = new Label(i, 0, title[i],setHeader());
                sheet.addCell(label);
            }


            for (int i = 0; i < content.length; i++) {
                for (int j = 0; j < content[i].length; j++) {
                    sheet.addCell(new Label(j,i+1,content[i][j]));

                }
            }


            workbook.write();
            workbook.close();
            os.close();
            Log.i(TAG, "initExcel success filePath:"+file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    public static WritableCellFormat setHeader(){
        WritableFont font = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);//字体
        WritableCellFormat writableCellFormat = new WritableCellFormat(font);
        try {
            font.setColour(Colour.BLUE_GREY);//字体颜色
            writableCellFormat.setAlignment(Alignment.CENTRE);//左右居中
            writableCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);//上下居中
            writableCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);//边框
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return writableCellFormat;
    }
}
