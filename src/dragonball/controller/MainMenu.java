package dragonball.controller;
import javax.swing.*;


import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.Game;
import dragonball.view.View;
import sun.audio.AudioData;
import sun.audio.*;
import sun.audio.AudioStream;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
public class MainMenu extends JLabel{
	private JButton newGame;
	private JButton loadGame;
	//private JLabel newGameL;
	private JButton exitGame;
	public JButton getNewGame() {
		return newGame;
	}
	public JButton getLoadGame() {
		return loadGame;
	}
public MainMenu(final View view){
	//super(new ImageIcon("MainMenuBG.gif"));
	super(new ImageIcon("MainMenu.jpg"));
	setSize(1366,780);
	newGame = new JButton(new ImageIcon("NewGameButton.png"));
	//newGame =  new JButton("New Game");
	//newGame.setFont(new java.awt.Font(null,1,30));
	newGame.setForeground(Color.WHITE);
	newGame.setRolloverIcon(new ImageIcon("NewGameButtonGlowed.png"));
	newGame.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			NewGame newGame = new NewGame(view);
			removeAll();
			repaint();
			add(newGame);
			newGame.setVisible(true);
		}
	});
	add(newGame);
	newGame.setBounds(450, 250, 460, 120);
	newGame.setBorder(BorderFactory.createEmptyBorder());
	newGame.setContentAreaFilled(false);
	
	loadGame = new JButton(new ImageIcon("LoadGameButton.png"));
	loadGame.setRolloverIcon(new ImageIcon("LoadGameButtonGlow.png"));
	loadGame.setFocusable(false);
	loadGame.addActionListener(new ActionListener() {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Game game = new Game();
				File load = new File("latestSave.txt");
				if(load.exists())
				try {
					game.load("latestSave.txt");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				else{
					JOptionPane.showMessageDialog(null, "You don't have any saved games.");
				}
			} catch (MissingFieldException | UnknownAttackTypeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
	});
	add(loadGame);
	loadGame.setBounds(450, 350, 460, 120);
	loadGame.setBorder(BorderFactory.createEmptyBorder());
	loadGame.setContentAreaFilled(false);
	
	exitGame = new JButton(new ImageIcon("ExitButton.png"));
	exitGame.setRolloverIcon(new ImageIcon("ExitButtonGlowed.png"));
	exitGame.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	});
	add(exitGame);
	exitGame.setBounds(450, 450, 460, 120);
	exitGame.setBorder(BorderFactory.createEmptyBorder());
	exitGame.setContentAreaFilled(false);
	//ImageIcon newGameLabel = new ImageIcon("menu-new-game.png");
	//Image scaleImage = newGameLabel.getImage().getScaledInstance(28, 28,Image.SCALE_DEFAULT);
	//newGameL = new JLabel(new ImageIcon("menu-new-game.png"));
	//add(newGameL);
	//newGameL.setBounds(600, 250, 100, 75);
	//music();
	}

public static void music() 
{       
    AudioPlayer MGP = AudioPlayer.player;
    AudioStream BGM;
    AudioData MD;
    ContinuousAudioDataStream loop = null;
    try
    {
        InputStream test = new FileInputStream("StayN.wav");
        BGM = new AudioStream(test);
        AudioPlayer.player.start(BGM);
  }
    catch(FileNotFoundException e){
        System.out.print(e.toString());
    }
    catch(IOException error)
    {
        System.out.print(error.toString());
    }
    MGP.start(loop);
}


}
