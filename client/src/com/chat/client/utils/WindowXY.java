package com.chat.client.utils;

import java.awt.*;

public class WindowXY {
	/**
	 * WuHuanye
	 * @return
	 */
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();
	public static int getWidth(){
		return toolkit.getScreenSize().width;
	}
	public static int getHeight(){
		return toolkit.getScreenSize().height;
	}





	//得到窗口的宽和高，进行计算显示的位置
	public static Point getXY() {
		//获得了屏幕的宽和高
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int height = toolkit.getScreenSize().height;
		int width = toolkit.getScreenSize().width;

		Point point = new Point(width, height);

		return point;
	}



}
