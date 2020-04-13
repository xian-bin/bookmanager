package com.book.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/CodeImg")
public class CodeImgServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 设置响应的格式类型
		resp.setHeader("Content-Type", "image/jpeg");
		// 定义图片的宽和高
		int w = 80;
		int h = 30;
		// 声明一个RGB格式的内存中的图片
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		// 创建一个绘制图像的对象
		Graphics g = img.getGraphics();
		// 把背景变白色
		g.setColor(Color.white);
		g.fillRect(0, 0, w, h);
		// 设置字体
		g.setFont(new Font("aa", Font.BOLD, 18));
		// 产生并draw出4个随机数字
		Random r = new Random();
		// 创建一个保存生成四个数的字符串
		String yzm = "";
		for (int i = 0; i < 4; i++) {
			int a = r.nextInt(10);// 生成0~9之间的随机整数
			int y = 15 + r.nextInt(10);// 产生随机的垂直位置
			// 产生随机颜色
			Color c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
			g.setColor(c);
			g.drawString("" + a, i * 15, y);
			// 把验证码转换为字符串追加到yzm中
			yzm += a;
		}
		// 把yzm放入session
		req.getSession().setAttribute("yzm", yzm);
		// 画几条干扰线
		for (int i = 0; i < 10; i++) {
			// 产生随机颜色
			Color c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
			g.setColor(c);
			g.drawLine(r.nextInt(60), r.nextInt(30), r.nextInt(60), r.nextInt(30));
		}
		g.dispose();// 类似于IO中的flush(),把图形数据刷到img中
		// 把内存图片 img 对象保存到一个 jpg 文件
		ImageIO.write(img, "JPEG", resp.getOutputStream());
	}
	
}
