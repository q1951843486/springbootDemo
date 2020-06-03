package com.cn.meiya.hxgcDevice;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IdCardReaderApplicationTests {

	@Test
	void contextLoads() {
	}

	
	static class JFrameBackground {

		private JFrame frame = new JFrame("美亚海钛");

		private JPanel imagePanel;

		private ImageIcon background;

		public JFrameBackground() {
			super();
		}

		public void setJFrame() {

			JLayeredPane layeredPane = new JLayeredPane();
			ImageIcon image = new ImageIcon(
					"D:\\Users\\liuhao\\eclipse-workspace-meiya\\IdCardReader\\src\\main\\resources\\static\\images\\background.png");// 随便找一张图就可以看到效果。 
			// 创建背景的那些东西
			JPanel jp = new JPanel();
			jp.setOpaque(false);
			jp.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
			JLabel jl = new JLabel(image);
			jp.add(jl);

			JLabel idCardLabel = new JLabel("身份证号码：");
			idCardLabel.setBounds(100, 100, 150, 40);
			JTextField idCardText = new JTextField();
			idCardText.setBounds(250, 100, 250, 40);
			layeredPane.add(idCardLabel, JLayeredPane.MODAL_LAYER);
			layeredPane.add(idCardText, JLayeredPane.MODAL_LAYER);

			JLabel nameLabel = new JLabel("姓名：");
			nameLabel.setBounds(100, 160, 150, 40);
			JTextField nameText = new JTextField();
			nameText.setBounds(250, 160, 250, 40);
			layeredPane.add(nameLabel, JLayeredPane.MODAL_LAYER);
			layeredPane.add(nameText, JLayeredPane.MODAL_LAYER);

			JLabel validityDateLabel = new JLabel("有效期起始日期：");
			validityDateLabel.setBounds(100, 220, 150, 40);
			JTextField validityDateText = new JTextField();
			validityDateText.setBounds(250, 220, 250, 40);
			layeredPane.add(validityDateLabel, JLayeredPane.MODAL_LAYER);
			layeredPane.add(validityDateText, JLayeredPane.MODAL_LAYER);
			
			JLabel resultLabel = new JLabel("核查结果：");
			resultLabel.setBounds(100, 280, 150, 40);
			JTextField resultText = new JTextField();
			resultText.setBounds(250, 280, 250, 40);
			layeredPane.add(resultLabel, JLayeredPane.MODAL_LAYER);
			layeredPane.add(resultText, JLayeredPane.MODAL_LAYER);

			JLabel promptsLabel = new JLabel("读取提示：");
			promptsLabel.setBounds(100, 340, 150, 40);
			JTextField promptsText = new JTextField();
			promptsText.setBounds(250, 340, 250, 40);
			layeredPane.add(promptsLabel, JLayeredPane.MODAL_LAYER);
			layeredPane.add(promptsText, JLayeredPane.MODAL_LAYER);

			// 创建一个测试按钮
			JButton jb = new JButton("核查");
			jb.setBounds((image.getIconWidth() - 240) / 2, image.getIconHeight() - 150, 100, 40);

			JButton dy = new JButton("打印");
			dy.setBounds(((image.getIconWidth() - 240) / 2 + 100 + 40), image.getIconHeight() - 150, 100, 40);

			// 将jp放到最底层。
			layeredPane.add(jp, JLayeredPane.DEFAULT_LAYER);
			// 将jb放到高一层的地方
			layeredPane.add(jb, JLayeredPane.MODAL_LAYER);
			layeredPane.add(dy, JLayeredPane.MODAL_LAYER);

			frame.setLayeredPane(layeredPane);
			frame.getLayeredPane().setLayout(null);
			frame.setSize(image.getIconWidth(), image.getIconHeight());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocation(image.getIconWidth(), 0);
			frame.setResizable(false);// 禁止缩放窗口
			frame.setVisible(true);

		}

		public void setJFrameBackground() {
			background = new ImageIcon(
					"D:\\\\Users\\\\liuhao\\\\eclipse-workspace-meiya\\\\IdCardReader\\\\src\\\\main\\\\resources\\\\static\\\\images\\\\background.png");// 背景图片
			JLabel label = new JLabel(background);// 把背景图片显示在一个标签里面
			// 把标签的大小位置设置为图片刚好填充整个面板
			label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
			// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
			imagePanel = (JPanel) frame.getContentPane();
			imagePanel.setOpaque(false);
			// 内容窗格默认的布局管理器为BorderLayout
			imagePanel.setLayout(new FlowLayout());

			JLabel idCardLabel = new JLabel("身份证号码：");
			idCardLabel.setBounds(10, 20, 80, 25);
			imagePanel.add(idCardLabel);
			JTextField idCardText = new JTextField(20);
			idCardText.setBounds(100, 20, 165, 25);
			imagePanel.add(idCardText);

			JLabel nameLabel = new JLabel("姓名：");
			nameLabel.setBounds(10, 60, 80, 25);
			imagePanel.add(nameLabel);
			JTextField nameText = new JTextField(20);
			nameText.setBounds(100, 60, 165, 40);
			imagePanel.add(nameText);

			JLabel validityDateLabel = new JLabel("有效期起始日期：");
			validityDateLabel.setBounds(10, 100, 80, 25);
			imagePanel.add(validityDateLabel);
			JTextField validityDateText = new JTextField(20);
			validityDateText.setBounds(100, 100, 165, 40);
			imagePanel.add(validityDateText);

			JButton button = new JButton("核查");
//		  button.setLocation(12, 371);
//		  button.setPreferredSize(new Dimension(116, 40));
			button.setBounds(100, 100, 100, 100);
			imagePanel.add(button);

//		  JButton button2 = new JButton("打印");
//		  
//		  imagePanel.add(button2);

			frame.getLayeredPane().setLayout(null);
			// 把背景图片添加到分层窗格的最底层作为背景
			frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(background.getIconWidth(), background.getIconHeight());
			frame.setResizable(false);
			frame.setVisible(true);
		}
	}
}
