package dragonball.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;
import dragonball.model.game.Game;
import dragonball.view.View;

public class DragonMode extends JLabel {
	private JButton senzuBeansButton;
	private JButton abilityPointsButton;
	private JButton superAttackButton;
	private JButton ultimateAttackButton;
	
	public DragonMode(Game game, WorldMap worldmap, Dragon dragon){ //geeeb attributes el dragon
		super(new ImageIcon("DragonMode.gif"));
		setSize(1366,780);
		
		DragonWish[] d = dragon.getWishes();
		
		senzuBeansButton = new JButton(new ImageIcon("BattleResources/GreenA.png"));
		senzuBeansButton.setRolloverIcon(new ImageIcon("BattleResources/GreenB.png"));
		
		senzuBeansButton.setBounds(20,150,500,200); //to be changed later (all the setbound values)
		senzuBeansButton.setBorder(BorderFactory.createEmptyBorder());
		senzuBeansButton.setContentAreaFilled(false);
		senzuBeansButton.setFocusable(false); 
		senzuBeansButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.getPlayer().chooseWish(d[0]);
				worldmap.worldInfoUpdater();
				setVisible(false);
				worldmap.setVisible(true);
				worldmap.revalidate();
			}
		});
		
		JLabel sB = new JLabel("",SwingConstants.CENTER);
		sB.setBounds(70, 146, 400, 200);
		sB.setFont(fontSetter("Physical Attack"));
		sB.setForeground(Color.WHITE);
		sB.setFocusable(false);
		sB.setText(d[0].getSenzuBeans() +" Senzu Beans");
		add(sB);
		add(senzuBeansButton);
		
		abilityPointsButton = new JButton(new ImageIcon("BattleResources/GreenA.png"));
		abilityPointsButton.setRolloverIcon(new ImageIcon("BattleResources/GreenB.png"));
		
		abilityPointsButton.setBounds(20,300,500,200); //to be changed later (all the setbound values)
		abilityPointsButton.setBorder(BorderFactory.createEmptyBorder());
		abilityPointsButton.setContentAreaFilled(false);
		abilityPointsButton.setFocusable(false); 
		abilityPointsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.getPlayer().chooseWish(d[1]);
				setVisible(false);
				worldmap.worldInfoUpdater();
				worldmap.setVisible(true);
				worldmap.revalidate();
			}
		});
		
		
		JLabel aP = new JLabel("",SwingConstants.CENTER);
		aP.setBounds(70, 299, 400, 200);
		aP.setFont(fontSetter("Physical Attack"));
		aP.setForeground(Color.WHITE);
		aP.setFocusable(false);
		aP.setText(d[1].getAbilityPoints() +" Ability Points");
		
		add(aP);
		add(abilityPointsButton);
		
		superAttackButton = new JButton(new ImageIcon("BattleResources/RedA.png"));
		superAttackButton.setRolloverIcon(new ImageIcon("BattleResources/RedB.png"));
		superAttackButton.setBounds(850,150,500,200); //to be changed later (all the setbound values)
		superAttackButton.setBorder(BorderFactory.createEmptyBorder());
		superAttackButton.setContentAreaFilled(false);
		superAttackButton.setFocusable(false); 
		superAttackButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				game.getPlayer().chooseWish(d[2]);
				setVisible(false);
				worldmap.worldInfoUpdater();
				worldmap.setVisible(true);
				worldmap.revalidate();
				
			}
		});
		
		JLabel sA = new JLabel("",SwingConstants.CENTER);
		sA.setBounds(900, 146, 400, 200);
		sA.setFont(fontSetter(d[2].getSuperAttack().getName()));
		sA.setForeground(Color.WHITE);
		sA.setFocusable(false);
		sA.setText(d[2].getSuperAttack().getName());
		
		add(sA);
		add(superAttackButton);
		
		ultimateAttackButton = new JButton(new ImageIcon("BattleResources/DarkBlueA.png"));
		ultimateAttackButton.setRolloverIcon(new ImageIcon("BattleResources/DarkBlueB.png"));
		ultimateAttackButton.setBounds(850,300,500,200); //to be changed later (all the setbound values)
		ultimateAttackButton.setBorder(BorderFactory.createEmptyBorder());
		ultimateAttackButton.setContentAreaFilled(false);
		ultimateAttackButton.setFocusable(false); 
		ultimateAttackButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				game.getPlayer().chooseWish(d[3]);
				setVisible(false);
				worldmap.worldInfoUpdater();
				worldmap.setVisible(true);
				worldmap.revalidate();
				
			}
		});
		
		JLabel uA = new JLabel("",SwingConstants.CENTER);
		uA.setBounds(900, 298, 400, 200);
		uA.setFont(fontSetter(d[3].getUltimateAttack().getName()));
		uA.setForeground(Color.WHITE);
		uA.setFocusable(false);
		uA.setText(d[3].getUltimateAttack().getName());
		
		add(uA);
		add(ultimateAttackButton);
		
	}

	private Font fontSetter(String attack){
		Font DynFont = null;
		if(attack.length()<=15){
			try {
			    //create the font to use. Specify the size!
			    DynFont = Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")).deriveFont(20.5f);
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    //register the font
			    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")));
			} catch (IOException e) {
			    e.printStackTrace();
			} catch(FontFormatException e) {
			    e.printStackTrace();
			}
		}
		else if(attack.length()<23){
			try {
			    //create the font to use. Specify the size!
			    DynFont = Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")).deriveFont(15f);
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    //register the font
			    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")));
			} catch (IOException e) {
			    e.printStackTrace();
			} catch(FontFormatException e) {
			    e.printStackTrace();
			}
		}
		else if(attack == "pInfo"){
			try {
			    //create the font to use. Specify the size!
			    DynFont = Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")).deriveFont(15f);
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    //register the font
			    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")));
			} catch (IOException e) {
			    e.printStackTrace();
			} catch(FontFormatException e) {
			    e.printStackTrace();
			}
		}
		else{
			try {
			    //create the font to use. Specify the size!
			    DynFont = Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")).deriveFont(13f);
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    //register the font
			    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")));
			} catch (IOException e) {
			    e.printStackTrace();
			} catch(FontFormatException e) {
			    e.printStackTrace();
			}
		}
		return DynFont;
	}
	
//	public void paintComponent(Graphics g){
//		super.paintComponent(g);
//		Image img = new ImageIcon("DragonMode.gif").getImage();
//		g.drawImage(img, 0, 0, null);
//	}
//
	
}



