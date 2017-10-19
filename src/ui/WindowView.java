package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowView {
	private JFrame mainFrame;
	
	public WindowView () {
		mainFrame = new JFrame ();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());
	}
	
	public void setView (NetworkView networkView) {
	}
}
	
