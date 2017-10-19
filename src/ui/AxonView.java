package ui;

import javax.swing.JTextField;

public class AxonView extends JTextField {
	
	public AxonView () {
		super();
		this.setEditable(false);
	}
	
	public void updateAxonWeight (double weight) {
		this.setText(String.valueOf(weight));
	}
}
