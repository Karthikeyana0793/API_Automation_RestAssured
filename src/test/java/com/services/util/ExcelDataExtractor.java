/**
 * 
 */
package com.services.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.services.beans.AddBookData;

public class ExcelDataExtractor {

	private static DecimalFormat decimalFormat= new DecimalFormat();

	public static Sheet getSheet(String sheetName) {
		HSSFSheet hssfSheet = null; 
		try {   
			
			String inputFile = Util.getRootDirectoryPath() + PropertyHandler.getProperty("InPutDataFile");

			FileInputStream file = new FileInputStream(new File(inputFile));

			if(inputFile.endsWith(".xlsx")) {
				XSSFWorkbook xssfWorkBook = new XSSFWorkbook(file);
				XSSFSheet xssfSheet = xssfWorkBook.getSheet(sheetName);
				return xssfSheet;
			}else if(inputFile.endsWith(".xls")) {			
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(file);
				hssfSheet = hssfWorkbook.getSheet(sheetName);  
				return hssfSheet;
			}
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		return null;
	}

	private static String decimalToString(double d){
		decimalFormat.setMaximumFractionDigits(0);  
		return  decimalFormat.format(d).replace(".", "").replace(",",null);
	}

	public String dateFormat(Date date){
		SimpleDateFormat sdf= new SimpleDateFormat("DD/MM/YY");
		return sdf.format(date);
	}

	public static String readExcelCell(Cell cell){

		switch(cell.getCellType()) {

		case NUMERIC:
			String temps=decimalToString(cell.getNumericCellValue());
			if(temps.trim().equalsIgnoreCase("")){ 
				return null;
			}else{
				return temps;
			}

		case STRING: 
			if(cell.getStringCellValue().trim().equalsIgnoreCase("")){  
				return null;
			}else{ 
				return  cell.getStringCellValue().trim();
			}
		default:
			break;
		}
		return "";
	}

	//To get last column number from the input sheet
	public int getLastColNumber(String sheetName){
		try{
			FileInputStream fis = new FileInputStream(PropertyHandler.getProperty("InPutDataFile"));  
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sh = wb.getSheet(sheetName);  
			if(sh.getRow(0)!=null){
				return sh.getRow(0).getLastCellNum()-1;
			}else{
				return 0;
			}       
		}catch(FileNotFoundException f){

		}catch(EncryptedDocumentException en) {

		}catch(IOException i) {

		}
		return 0;
	}

	/*
	 * Read excel data 
	 */
	public static AddBookData getAddBookData(Sheet sheet, int rowId){
		AddBookData addBookData;
		try{    

			if(sheet == null){
				return null;
			}

			addBookData = new AddBookData();     

			Row row = sheet.getRow(rowId);

			int columns=-1;
			if(row!=null){
				addBookData.settId(row.getCell(++columns)!=null ? readExcelCell(row.getCell(columns)): null);
				addBookData.setName(row.getCell(++columns)!=null ? readExcelCell(row.getCell(columns)): null);
				addBookData.setIsbn(row.getCell(++columns)!=null ? readExcelCell(row.getCell(columns)): null);
				addBookData.setAisle(row.getCell(++columns)!=null ? readExcelCell(row.getCell(columns)): null);
				addBookData.setAuthor(row.getCell(++columns)!=null ? readExcelCell(row.getCell(columns)): null);
			}   

			return addBookData;                  
		}
		catch(Exception e){

		}       
		return null;
	}

}
