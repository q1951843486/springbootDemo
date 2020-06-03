package com.cn.meiya.hxgcDevice;


import javax.swing.SwingUtilities;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cn.meiya.hxgcDevice.jFrameUI.AuditWindow;


@SpringBootApplication
public class IdCardReaderApplication {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new AuditWindow().setJFramePage();
			}
		});
		SpringApplication.run(IdCardReaderApplication.class, args);
	}
}
