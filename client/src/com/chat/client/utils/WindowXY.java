package com.chat.client.utils;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class WindowXY {

	//得到窗口的宽和高，进行计算显示的位置
	public static Point getXY(int w,int h) {
		//获得了屏幕的宽和高
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int height = toolkit.getScreenSize().height;
		int width = toolkit.getScreenSize().width;
		
		int y= (height-h)/2-50;
		
		Point point = new Point(w, y);
		
		return point;
	}
	
	public static Point getXY(Dimension d) {
		return getXY(d.width,d.height);
	}
}
