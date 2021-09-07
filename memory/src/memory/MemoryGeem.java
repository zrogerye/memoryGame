package memory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/*														to do list 
 * 											- possibly make quit game button???
 * 											- make sound shit work ////hello bictoorooror	
 */

public class MemoryGeem {
	static final int long_delay = 500, short_delay = 100;

	static int maxSelectButtonWidth;
	static Dimension sceensize;
	static Image background, sceen, congatSceen, armScreen;
	static JFrame levelSelectFrame, gameFrame, startFrame, congratSceenFrame;
	static JPanel levelSelect, gamePanel, gameBackground;
	static Level[] levels;

	public static void setupFrame(JFrame frame) {
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setFocusable(true);
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
	}

	public static void setup() {
		gameFrame = new JFrame("game frame");
		levelSelectFrame = new JFrame("level select frame");
		startFrame = new JFrame("StartFrame");
		congratSceenFrame = new JFrame("congratSceen");

		setupFrame(levelSelectFrame);
		setupFrame(startFrame);
		setupFrame(congratSceenFrame);
		setupFrame(gameFrame);

		sceensize = Toolkit.getDefaultToolkit().getScreenSize();
		background = Toolkit.getDefaultToolkit().createImage("brown-Guernsey-cow.jpg");
		Image sceensmall = Toolkit.getDefaultToolkit().createImage("startSceen.png");
		sceen = sceensmall.getScaledInstance((int) sceensize.getWidth(), (int) sceensize.getHeight(), Image.SCALE_DEFAULT);
		Image congat = Toolkit.getDefaultToolkit().createImage("happyPerretSieracke.png");
		congatSceen = congat.getScaledInstance((int) sceensize.getWidth(), (int) sceensize.getHeight(), Image.SCALE_DEFAULT);
		armScreen = Toolkit.getDefaultToolkit().createImage("armStrong.jpg");
		armScreen = armScreen.getScaledInstance((int) sceensize.getWidth(), (int) sceensize.getHeight(), Image.SCALE_DEFAULT);

		levelSelect = new JPanel(new GridBagLayout());
		levelSelect.setBackground(Color.BLACK);
		levelSelectFrame.setContentPane(levelSelect);


		gamePanel = new JPanel(new GridBagLayout());
		gameFrame.setContentPane(gamePanel);

		//gamePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		gamePanel.setBackground(Color.BLACK);
		//gamePanel.setOpaque(false);

		//test tets
		JButton goBackButton = new JButton("Go Back");
		goBackButton.setPreferredSize(new Dimension(100,25));
		//tets.setLocation(600,800);
		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent hi) {
				System.out.println("back button pressed");
				gameFrame.dispose();
				setup();
				levelSelectFrame.setVisible(true);
			}
		});
		goBackButton.setBackground(Color.WHITE);
		goBackButton.setFocusable(false);
		GridBagConstraints tats = new GridBagConstraints();		
		tats.gridy = 1;
		tats.insets = new Insets(10, 0, 0, 120);
		gamePanel.add(goBackButton, tats);
		tats.gridx = 0;
		tats.insets = new Insets(10, 120, 0, 0);
		JButton resetButton = new JButton("Reset");

		//resets the game
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent halow) {
				System.out.println("reset button pressed");

				gameFrame.dispose();
				setup();
				gamePanel.add(levels[Level.currentLevelIndex].buttonPanel);
				gameFrame.setVisible(true);

			}
		});
		resetButton.setBackground(Color.WHITE);
		resetButton.setPreferredSize(new Dimension(100,25));
		resetButton.setFocusable(false);
		gamePanel.add(resetButton, tats);
		goBackButton.setVisible(true);
		//no more test tetes

		JButton startButton = new JButton();
		startButton.setIcon(new ImageIcon(sceen));
		startButton.setLocation(0, 0);
		startButton.setFocusable(false);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("start button pressed");
				startFrame.dispose();
				//test.setVisible(false);
				levelSelectFrame.setVisible(true);
			}
		});
		startFrame.add(startButton);

		FontMetrics fm = new JButton().getFontMetrics(new JButton().getFont());

		String levelOneButtonText = "Level One";
		String levelTwoButtonText = "Level Two";
		String levelThreeButtonText = "Level Three";
		String levelOPButtonText = "Hmm.....";

		int width1 = fm.stringWidth(levelOneButtonText);
		int width2 = fm.stringWidth(levelTwoButtonText);
		int width3 = fm.stringWidth(levelThreeButtonText);
		int width4 = fm.stringWidth(levelOPButtonText);

		maxSelectButtonWidth = Collections.max(Arrays.asList(width1, width2, width3, width4));
		Insets margin = new JButton().getMargin();
		maxSelectButtonWidth += (margin.left + margin.right + 10) * 3;

		JButton congratSceenButton = new JButton(new ImageIcon(congatSceen));
		congratSceenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent helo) {
				System.out.println("congrats button pressed");
				congratSceenFrame.dispose();
				setup();
				levelSelectFrame.setVisible(true);
			}
		});
		congratSceenButton.setBorderPainted(false);
		congratSceenFrame.add(congratSceenButton);


		levels = new Level[4];
		Level.resetCounter();
		//makes button to bring to level 1
		levels[0] = new Level(4, 250, 100, levelOneButtonText, new Dimension(maxSelectButtonWidth, 500), new Dimension(69, 69), levelSelectFrame, gameFrame, congratSceenFrame, gamePanel);
		levels[1] = new Level(16, 250, 100, levelTwoButtonText, new Dimension(maxSelectButtonWidth, 500), new Dimension(69, 69), levelSelectFrame, gameFrame, congratSceenFrame, gamePanel);
		levels[2] = new Level(36, 250, 100, levelThreeButtonText, new Dimension(maxSelectButtonWidth, 500), new Dimension(69, 69), levelSelectFrame, gameFrame, congratSceenFrame, gamePanel);
		levels[3] = new Level(100, 250, 100, levelOPButtonText, new Dimension(maxSelectButtonWidth, 500), new Dimension(69, 69), levelSelectFrame, gameFrame, congratSceenFrame, gamePanel);

		GridBagConstraints space = new GridBagConstraints();
		space.insets = new Insets(3, 50, 3, 50);
		levelSelect.add(levels[0].levelSelectButton, space);
		levelSelect.add(levels[1].levelSelectButton, space);
		levelSelect.add(levels[2].levelSelectButton, space);
		levelSelect.add(levels[3].levelSelectButton, space);
	}

	public static void main(String[] args) {

		setup();
		startFrame.setVisible(true);
	}

	static void test0() {
		System.out.println("test 0");
	}

	static void test1() {
		System.out.println("test 1");
	}

	static void test2() {
		System.out.println("test 2");
	}
	
	static void test3() {
		System.out.println("test 3");
	}
	
	static void test4() {
		System.out.println("test 4");
	}

	static void setUpLevelsFrame() {
		System.out.println("hello");
	}
}