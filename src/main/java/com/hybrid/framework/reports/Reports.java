package com.hybrid.framework.reports;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static com.hybrid.framework.platforms.Browsers.*;


public class Reports {
	
	public static XSSFWorkbook setwbook;
	public static XSSFSheet sheet;
	
	
	public static void setReports(String sheetName, int columnNo, int rowNo, String xlData) throws IOException {
		
		FileOutputStream fos=null;
		
		try{
			setwbook=new XSSFWorkbook(oFilepath);
		
		sheet =setwbook.getSheet(sheetName);
		if(sheet.getRow(rowNo)==null){
			Cell setcel=sheet.createRow(rowNo).createCell(columnNo);
			setcel.setCellValue(xlData);
		}
		else{
		Cell setcel=sheet.getRow(rowNo).createCell(columnNo);
		setcel.setCellValue(xlData);
		}
		fos=new FileOutputStream(setwbook.getSheet(sheetName).getSheetName());
		setwbook.write(fos);
		System.out.println("File written susseccfully ");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	finally{
		System.out.println(xlData);
		fos.flush();
		fos.close();
		setwbook.close();
	}
		
	}	
	
	
	
}
