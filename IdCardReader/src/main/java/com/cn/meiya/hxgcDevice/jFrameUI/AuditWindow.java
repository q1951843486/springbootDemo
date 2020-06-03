/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package com.cn.meiya.hxgcDevice.jFrameUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.cn.meiya.hxgcDevice.bean.CheckResult;
import com.cn.meiya.hxgcDevice.cardReader.ICSDK;
import com.cn.meiya.hxgcDevice.service.CallApi;
import com.cn.meiya.hxgcDevice.service.PDFPrint;
import com.cn.meiya.hxgcDevice.util.DateUtil;
import com.cn.meiya.hxgcDevice.util.FileUtil;
import com.cn.meiya.hxgcDevice.util.PDFUtils;

/**
 * 
 * Description: 核查界面
 * @author LiuHao
 * @date 2020年6月2日下午12:12:01
 * @version 1.0
 */
@Component
public class AuditWindow {
	
	/**
	 * 桌面窗口
	 */
	private static JFrame frame = new JFrame("美亚海钛V1.0");
	/**
	 * 身份证号文本框
	 */
	private static JTextField idCardText;
	/**
	 * 姓名文本框
	 */
	private static JTextField nameText;
	/**
	 * 有效期起始日文本框
	 */
	private static JTextField validityDateText;
	/**
	 * 核查结果文本框
	 */
	private static JTextField resultText;
	/**
	 * 提示信息文本框
	 */
	private static JTextField promptsText;
	
	
	private static CheckResult checkResult;

	/**
	 * 
	 * Description: 设置界面样式
	 */
	public void setJFramePage() {

		JLayeredPane layeredPane = new JLayeredPane();
		ImageIcon image = new ImageIcon(FileUtil.inputStream2byte(FileUtil.getFileInputStream("/static/images/background.png")));// 随便找一张图就可以看到效果。 
		// 创建背景的那些东西
		JPanel jp = new JPanel();
		jp.setOpaque(false);
		/*
		 * 这个方法定义了组件的位置。 setBounds(x, y, width, height) x 和 y 指定左上角的新位置，由 width 和 height
		 * 指定新的大小。
		 */
		jp.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
		JLabel jl = new JLabel(image);
		jp.add(jl);

		JLabel idCardLabel = new JLabel("身份证号码：");
		idCardLabel.setBounds(250, 100, 150, 40);
		idCardLabel.setFont(new Font("微软雅黑",Font.PLAIN,14));
		idCardText = new JTextField();
		idCardText.setBounds(400, 100, 250, 40);
		idCardText.setEditable(false);//不可编辑
		idCardText.setOpaque(false);//透明
		idCardText.setFont(new Font("微软雅黑",Font.PLAIN,14));
		idCardText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));//不显边框
		layeredPane.add(idCardLabel, JLayeredPane.MODAL_LAYER);
		layeredPane.add(idCardText, JLayeredPane.MODAL_LAYER);

		JLabel nameLabel = new JLabel("姓名：");
		nameLabel.setBounds(250, 150, 150, 40);
		nameLabel.setFont(new Font("微软雅黑",Font.PLAIN,14));
		nameText = new JTextField();
		nameText.setBounds(400, 150, 250, 40);
		nameText.setEditable(false);
		nameText.setOpaque(false);
		nameText.setFont(new Font("微软雅黑",Font.PLAIN,14));
		nameText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		layeredPane.add(nameLabel, JLayeredPane.MODAL_LAYER);
		layeredPane.add(nameText, JLayeredPane.MODAL_LAYER);

		JLabel validityDateLabel = new JLabel("有效期起始日：");
		validityDateLabel.setBounds(250, 200, 150, 40);
		validityDateLabel.setFont(new Font("微软雅黑",Font.PLAIN,14));
		validityDateText = new JTextField();
		validityDateText.setBounds(400, 200, 250, 40);
		validityDateText.setEditable(false);
		validityDateText.setOpaque(false);
		validityDateText.setFont(new Font("微软雅黑",Font.PLAIN,14));
		validityDateText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		layeredPane.add(validityDateLabel, JLayeredPane.MODAL_LAYER);
		layeredPane.add(validityDateText, JLayeredPane.MODAL_LAYER);
		
		//核查结果
		JLabel resultLabel = new JLabel("");
		resultLabel.setBounds(0, 0, 0, 0);
		resultText = new JTextField();
		resultText.setBounds(0, 250, image.getIconWidth(), 60);
		resultText.setEditable(false);
		resultText.setOpaque(false);
		resultText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		resultText.setHorizontalAlignment(JTextField.CENTER);//居中
		resultText.setFont(new Font("微软雅黑",Font.BOLD,20));
//		layeredPane.add(resultLabel, JLayeredPane.MODAL_LAYER);
		layeredPane.add(resultText, JLayeredPane.MODAL_LAYER);

		//提示信息
		JLabel promptsLabel = new JLabel("");
		promptsLabel.setBounds(250, 320, 150, 40);
		promptsText = new JTextField();
		promptsText.setBounds(250, 320, image.getIconWidth(), 40);
		promptsText.setEditable(false);
		promptsText.setOpaque(false);
		promptsText.setFont(new Font("宋体",Font.PLAIN,14));
		promptsText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
//		layeredPane.add(promptsLabel, JLayeredPane.MODAL_LAYER);
		layeredPane.add(promptsText, JLayeredPane.MODAL_LAYER);

		// 创建一个测试按钮
		JButton button = new JButton("核查");
		button.setBounds((image.getIconWidth() - 240) / 2, image.getIconHeight() - 100, 100, 40);
		button.setFont(new Font("微软雅黑",Font.PLAIN,20));
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		addActionListener(button);
		JButton dy = new JButton("打印");
		dy.setFont(new Font("微软雅黑",Font.PLAIN,20));
		dy.setBackground(Color.BLUE);
		dy.setForeground(Color.WHITE);
		dy.setBounds(((image.getIconWidth() - 240) / 2 + 100 + 40), image.getIconHeight() - 100, 100, 40);
		addActionListener(dy);

		// 将jp放到最底层。
		layeredPane.add(jp, JLayeredPane.DEFAULT_LAYER);
		// 将jb放到高一层的地方
		layeredPane.add(button, JLayeredPane.MODAL_LAYER);
		layeredPane.add(dy, JLayeredPane.MODAL_LAYER);

		frame.setLayeredPane(layeredPane);
		frame.getLayeredPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(image.getIconWidth(), image.getIconHeight());
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize(); // 获得显示器大
		Dimension frameSize = frame.getSize();             // 获得窗口大小对象  
		if (frameSize.width > displaySize.width)  
		frameSize.width = displaySize.width;           // 窗口的宽度不能大于显示器的宽度  
		frame.setLocation((displaySize.width - frameSize.width) / 2,  
				(displaySize.height - frameSize.height) / 2); // 设置窗口居中显示器显示 
		frame.setIconImage(new ImageIcon(FileUtil.inputStream2byte(FileUtil.getFileInputStream("/static/images/32123.png"))).getImage());
		frame.setResizable(false);// 禁止缩放窗口
		frame.setVisible(true);
//		frame.dispose(); // 如果写这句可实现窗口关闭，springboot项目仍运行
	}

	/**
	 * 
	 * Description: 加载文本框值
	 * @param idCard
	 * @param name
	 * @param validityDate
	 * @param result
	 * @param msg
	 */
	private static void loadingText(String idCard,String name,String validityDate,String result,String msg) {
		idCardText.setText(idCard);
		nameText.setText(name);
		validityDateText.setText(validityDate);
		resultText.setText(result);
		promptsText.setText(msg);
	}
	
	/**
	 * 
	 * Description: refreshText
	 */
	private static void refreshText() {
		frame.repaint();
//		idCardText.repaint();
//		nameText.repaint();
//		validityDateText.repaint();
//		resultText.repaint();
//		promptsText.repaint();
	}

	/**
	 * 
	 * Description: 监听事件
	 * @param jButton
	 */
	private void addActionListener(JButton jButton) {
		// 为按钮绑定监听器
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String jButtonName = ae.getActionCommand();
				String msg = "";
				if(StringUtils.equals(jButtonName, "核查")) {//核查事件
					loadingText("","","","","读取......");
					checkResult = null;
					ICSDK icsdk = ICSDK.getInstance();
					String idCard = icsdk.getIc().trim();
					String name = icsdk.getName().trim();
					String validityDate = DateUtil.getSpecifiedDate(icsdk.getBegindate().trim(), "yyyyMMdd", "yyyy年MM月dd日");
					boolean isrRead = (StringUtils.isNotEmpty(idCard) && StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(validityDate)) ? true : false;//是否读到身份证信息
					if(isrRead) {
						msg = "读取身份证信息成功！";
						loadingText(idCard,name,validityDate,"联网查询中...",msg);
						String result = "您的证件已过有效期";
						try {
							checkResult = CallApi.doCheck(icsdk.getName(),icsdk.getIc());
							checkResult.setBegindate(validityDate);
							msg = "联网查询成功！";
							switch (checkResult.getStatusCode()) {
							case "200":
								if(StringUtils.isNotEmpty(checkResult.getErrorMesage())) {
									result = "联网查询失败！";
									msg = checkResult.getErrorMesage();
								}
								if(StringUtils.equals(checkResult.getResultIdCard(), "一致")) {
									resultText.setForeground(Color.GREEN);
								}else {
									resultText.setForeground(Color.RED);
								}
								result = checkResult.getResultIdCard();
								result = checkResult.getResult();
								break;
							case "-1":
								msg = checkResult.getErrormsg();
								break;
							case "-2":
								msg = checkResult.getErrormsg();
								break;
							default:
								
								break;
							}
							System.out.println(checkResult);
							loadingText(idCard,name,validityDate,result,msg);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							msg = e.getMessage();
							result = "程序出错了，请稍候再试...";
						}
						loadingText(idCard,name,validityDate,result,msg);
					}else {
						loadingText("","","","","错误：读取身份证信息失败...");
					}
					refreshText();
				}else if(StringUtils.equals(jButtonName, "打印")) {
					//打印事件
					PDFPrint pdfPrint = new PDFPrint();
					if(StringUtils.isNotEmpty(idCardText.getText())) {
						String pdfPath = pdfPrint.print(checkResult);
						try {
							PDFUtils.PDFprint(new File(pdfPath), "PDF");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
	}
}
