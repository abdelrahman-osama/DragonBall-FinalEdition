package dragonball.controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.Game;
import dragonball.view.View;
import javafx.scene.text.Font;

//note to self e3mel back button hena 
public class MapCreateFighter extends JLabel {
private JTextField playerName;
private JTextField fighterName;
private JLabel playerN;
private JLabel fighterN;
private JButton createFighter;
private JLabel chooseFighter;
private JButton Saiyan;
private JButton Namekian;
private JButton Earthling;
private JButton Frieza;
private JButton Majin;
private JButton FClicked;
private char Type;
//fe 7aga esmaha clear
public MapCreateFighter(WorldMap worldMap,Game game){
	super(new ImageIcon("MainMenu.jpg"));
	//super(new ImageIcon("DragonMode.gif"));
	setSize(1366,780);
	//set layout to grid
	createFighter = new JButton(new ImageIcon("NewGameResources/CreateFighter.png"));
	createFighter.setRolloverIcon(new ImageIcon("NewGameResources/CreateFighterB.png"));
	createFighter.setFocusable(false);
	createFighter.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(fighterName.getText().equals("")){
				JOptionPane.showMessageDialog(null, "You need to choose a Fighter Name!"); 
				return;
			}
			if(FClicked==null){
				JOptionPane.showMessageDialog(null, "You need to choose a Fighter!"); 
				return;
			}
			worldMap.setVisible(true);
			game.getPlayer().createFighter(Type, fighterName.getText());
		}
	});
	add(createFighter);
	createFighter.setBounds(450,630,460,120); //to be changed later (all the setbound values)
	createFighter.setBorder(BorderFactory.createEmptyBorder());
	createFighter.setContentAreaFilled(false);
	createFighter.setFont(new java.awt.Font(null, 1, 20));
	
	JButton goback = new JButton(new ImageIcon(""));
	goback.setRolloverIcon(new ImageIcon(""));
	goback.setFocusable(false);
	goback.setBounds(250, 32,460,120);
	goback.setBorder(BorderFactory.createEmptyBorder());
	goback.setContentAreaFilled(false);
	
	
	JLabel fighterN = new JLabel (new ImageIcon("fnjl.png"));
	fighterN.setBorder(BorderFactory.createEmptyBorder());
	add(fighterN);
	fighterN.setBounds(310, 350, 500, 500);
	fighterN.setFont(new java.awt.Font(null, 1, 20));
	fighterN.setForeground(Color.BLACK);
	
	fighterName = new JTextField(1);
	add(fighterName);
	fighterName.setBounds(670, 580, 150, 35);
	
	JLabel chooseFighter = new JLabel (new ImageIcon("caf.png"));
	add(chooseFighter);
	chooseFighter.setBounds(420, -150, 500, 500);
	chooseFighter.setFont(new java.awt.Font(null, 1, 40));
	chooseFighter.setForeground(Color.WHITE);
	
	Saiyan = new JButton(new ImageIcon("Saiyanf.png"));
	Saiyan.setRolloverIcon(new ImageIcon("SaiyanGlow.png"));
	Saiyan.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CheckChange('S');
			Type='S';
			FClicked=(JButton) e.getSource();
			FClicked.setIcon(FClicked.getRolloverIcon());
		}
	});
	add(Saiyan);
	Saiyan.setBounds(100,120,200,440); //to be changed later (all the setbound values)
	Saiyan.setBorder(BorderFactory.createEmptyBorder());
	Saiyan.setContentAreaFilled(false);
	Saiyan.setFocusable(false);
	
	Namekian = new JButton(new ImageIcon("Namekianf.png"));
	Namekian.setRolloverIcon(new ImageIcon("NamekianGlow.png"));
	Namekian.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CheckChange('N');
			Type='N';
			FClicked=(JButton) e.getSource();
			FClicked.setIcon(FClicked.getRolloverIcon());
		}
	});
	add(Namekian);
	Namekian.setBounds(350,120,200,440); //to be changed later (all the setbound values)
	Namekian.setBorder(BorderFactory.createEmptyBorder());
	Namekian.setContentAreaFilled(false);
	Namekian.setFocusable(false);
	Earthling = new JButton(new ImageIcon("Earthlingf.png"));
	Earthling.setRolloverIcon(new ImageIcon("EarthlingGlow.png"));
	Earthling.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CheckChange('E');
			Type='E';
			FClicked=(JButton) e.getSource();
			FClicked.setIcon(FClicked.getRolloverIcon());
		}
	});
	add(Earthling);
	Earthling.setBounds(600,120,200,440); //to be changed later (all the setbound values)
	Earthling.setBorder(BorderFactory.createEmptyBorder());
	Earthling.setContentAreaFilled(false);
	Earthling.setFocusable(false);
	Frieza = new JButton(new ImageIcon("Friezaf.png"));
	Frieza.setRolloverIcon(new ImageIcon("FriezaGlow.png"));
	Frieza.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CheckChange('F');
			Type='F';
			FClicked=(JButton) e.getSource();
			FClicked.setIcon(FClicked.getRolloverIcon());
		}
	});
	add(Frieza);
	Frieza.setBounds(850,120,200,440); //to be changed later (all the setbound values)
	Frieza.setBorder(BorderFactory.createEmptyBorder());
	Frieza.setContentAreaFilled(false);
	Frieza.setFocusable(false);
	Majin = new JButton(new ImageIcon("Majinf.png"));
	Majin.setRolloverIcon(new ImageIcon("MajinGlow.png"));
	Majin.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CheckChange('M');
			Type='M';
			FClicked=(JButton) e.getSource();
			FClicked.setIcon(FClicked.getRolloverIcon());
		}
	});
	add(Majin);
	Majin.setBounds(1100,120,200,440); //to be changed later (all the setbound values)
	Majin.setBorder(BorderFactory.createEmptyBorder());
	Majin.setContentAreaFilled(false);
	Majin.setFocusable(false);
}
private void CheckChange(char c) {
	if(c==Type)
		return;
	switch(Type){
	case'S':Saiyan.setIcon(new ImageIcon("Saiyanf.png"));break;
	case'N':Namekian.setIcon(new ImageIcon("Namekianf.png"));break;
	case'E':Earthling.setIcon(new ImageIcon("Earthlingf.png"));break;
	case'F':Frieza.setIcon(new ImageIcon("Friezaf.png"));break;
	case'M':Majin.setIcon(new ImageIcon("Majinf.png"));break;
	}
	
}
public JTextField getPlayerName() {
	return playerName;
	}
public JTextField getFighterName() {
	return fighterName;
	}
}