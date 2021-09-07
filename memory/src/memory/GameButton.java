package memory;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;

public class GameButton extends JButton {

	private static int counter = 0;
	private Timer timer;
	String text, save_text;//gay
	
	final int id;

	class GameTimerTask extends TimerTask {

		@Override
		public void run() {
			setText("");
			setBackground(Color.white);
		}
	}

	public GameButton() {
		text = "";
		id = counter++;
	}

	public GameButton(String text) {
		super(text);
		this.text = this.save_text = text;
		timer = new Timer();
		id = counter++;
	}

	@Override
	public void setText(String text) {
		this.text = text;
		super.setText(text);
	}

	public void flip(int millis) {
		timer.schedule(new GameTimerTask(), millis);
	}

}