package com.liyuanhong.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.liyuanhong.listener.ChooseFile;
import com.liyuanhong.listener.ConvertFile;

public class OperationWindow {	
		public void init(){
		JFrame frame = new JFrame("android自动化脚本转换器");
		frame.setLayout(new FlowLayout());
		JButton selectFile = new JButton("选择");
		JTextField filePathFild = new JTextField();
		JButton convertTo = new JButton("转换");
		
		filePathFild.setColumns(35);
		
		selectFile.addMouseListener(new ChooseFile(filePathFild, frame));
		convertTo.addMouseListener(new ConvertFile(frame, filePathFild));
		
		frame.add(selectFile);
		frame.add(filePathFild);
		frame.add(convertTo);
		
		frame.pack();
		setWindowCenter(frame);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		
	public void setWindowCenter(JFrame frame){
		int windowWidth = frame.getWidth();                    //获得窗口宽
        int windowHeight = frame.getHeight();                  //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit();             //定义工具包
        Dimension screenSize = kit.getScreenSize();            //获取屏幕的尺寸
        int screenWidth = screenSize.width;                    //获取屏幕的宽
        int screenHeight = screenSize.height;                  //获取屏幕的高
        frame.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);
	}
}
