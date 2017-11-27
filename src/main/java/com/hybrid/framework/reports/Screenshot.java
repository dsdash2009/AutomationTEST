package com.hybrid.framework.reports;

import static com.hybrid.framework.execution.Parameterization.driver;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.hybrid.framework.execution.Parameterization;

public class Screenshot {
	
	public static String screenPath = System.getProperty("user.dir")+"/src/main/resources/screenshots/";
		
	
	public static String getScreenshot(String xpath) throws IOException{   
		//get the fullpage screenshot
		Calendar calendar= Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String actualImageName = screenPath+Parameterization.testCaseID+"_"+formater.format(calendar.getTime())+".png";
		
		//Focus on the specific web element
		WebElement autoCapt = driver.findElement(By.xpath(xpath)); 
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		BufferedImage bi = ImageIO.read(screenshot);
		
		//Get the location of element on the page
		Point p = autoCapt.getLocation();
		bi.getTransparency();
		Graphics g = bi.getGraphics();
		g.setColor(Color.RED.brighter());
		
		//Get width and height of the element
		g.drawRect(p.getX(), p.getY(), autoCapt.getSize().width, autoCapt.getSize().height);
		g.setFont(new Font("verdana", Font.BOLD, 16));
		g.setColor(Color.RED.brighter());
		g.dispose();
		ImageIO.write(bi, "png", screenshot);
		
		//copy file to disc
		File destFile = new File(actualImageName);
		try {
			FileUtils.copyFile(screenshot, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return actualImageName;
		
	}
	
}
