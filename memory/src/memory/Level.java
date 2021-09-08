package memory;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.DataLine.Info;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Level implements Runnable {

	private static int counter;
	static int currentLevelIndex;

	String label;
	int total;
	int id;
	int pressedSize, numDone, longDelay, shortDelay;
	ArrayList<GameButton> pressed;

	JPanel buttonPanel;
	JButton levelSelectButton;

	public Level(int total, int longDelay, int shortDelay, String selectLabel, Dimension preferredLevelSelectButtonSize, Dimension preferredGameButtonSize, JFrame levelSelectFrame, JFrame gameFrame, JFrame congratSceenFrame, JPanel gamePanel) {
		pressed = new ArrayList<GameButton>();
		id = counter++;
		this.label = selectLabel;
		this.total = total;
		this.longDelay = longDelay;
		this.shortDelay = shortDelay;
		buttonPanel = new JPanel(new GridLayout((int) Math.sqrt(total), (int) Math.sqrt(total), 1, 1));
		makeLevelSelectButton(preferredLevelSelectButtonSize, levelSelectFrame, gameFrame, gamePanel);
		addButtons(preferredGameButtonSize, gameFrame, congratSceenFrame, buttonPanel);
	}

	private void addButtons(Dimension preferredSize, JFrame gameFrame, JFrame congratSceenFrame, JPanel buttonPanel) {
		ArrayList<Integer> choices = new ArrayList<Integer>();
		for (int z = 1; z <= total / 2; z++) {
			choices.add(z);
			choices.add(z);
		}
		Collections.shuffle(choices);
		for(int i = 1; i <= total; i++) {
			String j = String.valueOf(choices.remove(0));
			GameButton button = new GameButton("");
			button.save_text = j;

			button.setBorderPainted(true);
			button.setFocusPainted(false);
			button.setBackground(Color.WHITE);
			button.setPreferredSize(new Dimension(69,69));

			//set button image here
			Image armScreen = Toolkit.getDefaultToolkit().createImage("armStrong.jpg");
			//button.setIcon(new ImageIcon(armScreen));

			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {


					//put sound thing here
					Runnable mooSound = () -> {
						final testAudio player = new testAudio ();
						player.play("C:\\Users\\roger\\git\\memory\\memory\\moo.wav");
					};

					//new Thread(mooSound).start();



					if (pressed.size() == 1 && button.id == pressed.get(0).id)
						return;
					if (pressed.size() < 2) {
						//System.out.println(button.getBounds().x + " " + button.getBounds().y);c
						button.setText(button.save_text);
						button.setBackground(Color.CYAN);

						pressed.add(button);
						pressedSize++;
					}
					if (pressedSize >= 2) {
						GameButton one = pressed.get(0), two = pressed.get(1);
						boolean correct = one.text.equals(two.text);
						if (!correct) {
							one.setBackground(Color.RED);
							two.setBackground(Color.RED);
							one.flip(longDelay);
							two.flip(longDelay);
						}
						else {
							numDone += 2;
							one.setBackground(Color.GREEN);
							two.setBackground(Color.GREEN);
							one.setEnabled(false);
							two.setEnabled(false);
						}
						pressedSize -= 2;
						new Timer().schedule(new TimerTask() {
							public void run() {
								pressed.remove(0);
								pressed.remove(0);
							}
						}, correct ? shortDelay : longDelay);
					}
					//System.out.println("num_done = " + numDone);
					//System.out.println("total = " + total);
					if (numDone >= total) {
						System.out.println("level complete");

						gameFrame.dispose();
						congratSceenFrame.setVisible(true);

						Runnable goodjobSound = () -> {
							final testAudio player = new testAudio ();
							player.play("C:\\Users\\roger\\git\\memory\\memory\\goodjob.wav");
						};

						new Thread(() -> {
							for (int t = 0; t < 50; t++) {
								new Thread(goodjobSound).start();
								try {
									Thread.sleep(100);
								} catch (Exception e) { }
							}
						}).start();

						return;
					}
				}
			});
			button.setFocusable(false);
			buttonPanel.add(button);
		}
	}

	private void makeLevelSelectButton(Dimension preferredSize, JFrame levelSelectFrame, JFrame gameFrame, JPanel gamePanel)/*hello*/ {
		levelSelectButton = new JButton(label);
		levelSelectButton.setPreferredSize(preferredSize);
		levelSelectButton.setBackground(Color.WHITE);
		//levelSelectButton.setBorderPainted(false);
		levelSelectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentLevelIndex = id;
				gamePanel.add(buttonPanel);
				levelSelectFrame.dispose();
				gameFrame.setVisible(true);
			}
		});
		levelSelectButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				levelSelectButton.setText(null);
				Image armScreen = Toolkit.getDefaultToolkit().createImage("armStrong.jpg");
				Image armScreenResized = armScreen.getScaledInstance(preferredSize.width, preferredSize.height, Image.SCALE_DEFAULT);
				levelSelectButton.setIcon(new ImageIcon(armScreenResized));
			}

			public void mouseExited(MouseEvent evt) {
				levelSelectButton.setIcon(null);
				levelSelectButton.setText(label);
			}
		});
		levelSelectButton.setFocusable(false);
	}

	public static void resetCounter() {
		counter = 0;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		final testAudio player = new testAudio ();
		player.play("C:\\Users\\roger\\Documents\\EclipseWorkspace\\test\\moo.wav");

	}		










	//test


}