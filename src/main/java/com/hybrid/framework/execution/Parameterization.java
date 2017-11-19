package com.hybrid.framework.execution;

import java.io.File;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import static com.hybrid.framework.reports.Reports.*;
import static com.hybrid.framework.platforms.Browsers.*;

public class Parameterization {
	
public static String Filepath = "./src/main/resources/input.xlsx";
public static String SheetName = "configuration";
public static String SheetName1 = "objects";
public static XSSFWorkbook wbook;
public static XSSFSheet sheet;

public static String testCaseID;
public static WebDriver driver;
public static String gxpath;
	
// Read the XL report
public static String getContent(String Filepath, String SheetName, String ColumName, int Rowno) throws IOException{
	
		try{
			wbook=new XSSFWorkbook(new File(Filepath));
			sheet =wbook.getSheet(SheetName);
			int Colm=getColumNum(SheetName,ColumName);
			
			if(sheet.getRow(Rowno)==null){
				return null;
						}
				else{
					DataFormatter formatter = new DataFormatter();
					return formatter.formatCellValue(sheet.getRow(Rowno).getCell(Colm));
					}
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
		
public static int getColumNum(String SheetName,String ColumName) throws IOException{
	
try{
		
	sheet =wbook.getSheet(SheetName);
	for(int i=0;i<sheet.getLastRowNum();i++){
		for(int j=0;j<sheet.getRow(0).getLastCellNum();j++){
			if(sheet.getRow(0).getCell(j).getStringCellValue().equals(ColumName)){
			return sheet.getRow(i).getCell(j).getAddress().getColumn();
			}	
		}
	}
	
}
	catch(Exception e){
		e.printStackTrace();
		
	}
return 0;
	
}

// To read the column values in XL using while loop

public static void run() throws IOException{
	int a=1;
	
	while(true){
		
	String actioncoloumn = getContent(Filepath, SheetName1, "Action", a);
	String xpath = 	getContent(Filepath, SheetName1, "X-path Locator", a);
	String values = getContent(Filepath, SheetName1, "Values", a);
		
	String end = getContent(Filepath, SheetName1, "Testcase id", a);
	testCaseID = end;
	gxpath=xpath;
	String expected = getContent(Filepath, SheetName1, "Expected Content", a);
	String readyTest = getContent(Filepath, SheetName1, "Ready to test – (Yes/No)", a);
	
	if(end.equalsIgnoreCase("end")){
		break;
	}
	
	if(readyTest.equalsIgnoreCase("yes")){
		Keywords.keyword(actioncoloumn, xpath, values, expected, a, a);
	
	}

else{
	
	//System.out.println("Not Tested, Set the \"end\" position part correctly in XLSheet");
	if(!readyTest.isEmpty() && readyTest.length()!=0){
		setReports("objects", 9, a, "Not ready");
	}
}
	
a++;	
	}
	
}

public static void exit() throws  IOException{
	driver.quit();
	System.out.println("******Testcases Completed******");
	
	
}

// Main functions

public static void main(String[] args) throws IOException  {
	
	Browser();
	run();
    exit();
	
	}				
    

	
}