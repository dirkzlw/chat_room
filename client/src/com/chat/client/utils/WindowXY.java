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

}
