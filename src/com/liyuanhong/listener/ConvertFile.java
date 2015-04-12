package com.liyuanhong.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.liyuanhong.util.ConverMethod;

public class ConvertFile extends MouseAdapter{
	private JFrame frame;
	private File file;
	private JTextField filePathFild;
	private String temp = "";
	private FileOutputStream outputStream;
	private OutputStreamWriter outputWriter;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private String oldFile = "";
	private String newFile = "";
	
	public ConvertFile(JFrame frame,JTextField filePathFild) {
		this.frame =frame;
		this.filePathFild = filePathFild;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		temp = filePathFild.getText();
		oldFile = temp;
		
		if(temp.equals("")){
			
		}else{
			temp = oldFile.substring(0, (oldFile.length() - 3));
			newFile = temp + "py";
			file = new File(newFile);
			try {
				file.createNewFile();	
				writeToFile(file,oldFile);
				JOptionPane.showMessageDialog(frame, "转换成功！", "信息", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(frame, "转换失败！", "错误", JOptionPane.ERROR);
			}
		}
	}
	
	//创建转换后的文件
	public void writeToFile(File file,String path){
		try {
			outputStream = new FileOutputStream(file);
			outputWriter = new OutputStreamWriter(outputStream);
			fileReader = new FileReader(path);
			bufferedReader = new BufferedReader(fileReader);
			String line = "";
			
			outputWriter.write("from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice\n");
			outputWriter.flush();
			outputWriter.write("device = MonkeyRunner.waitForConnection()\n");
			outputWriter.flush();
			
			while((line = bufferedReader.readLine()) != null){				
				line = converLine(line);
				outputWriter.write(line + "\n");
				outputWriter.flush();
			}
			
			outputWriter.close();
			outputStream.close();
			bufferedReader.close();
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//返回被转换后的一行代码
	public String converLine(String line){
		String newLine = "";
		newLine = ConverMethod.toConvert(line);		
		return newLine;
	}
}
