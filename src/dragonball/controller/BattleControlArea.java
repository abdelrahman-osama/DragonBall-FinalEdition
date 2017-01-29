package dragonball.controller;

import javax.swing.*;
//NOT DONE YET
public class BattleControlArea extends JLabel {
private JButton attack;
private JButton block;
private JButton use;
private JButton endTurn;
//private JLabel CurrentTurn;
public BattleControlArea(){
	super();
	attack = new JButton("Attack");
	add(attack);
	attack.setBounds(130,50,100,40);
	
	block = new JButton("Block");
	add(block);
	block.setBounds(130,0,100,40);
	
	endTurn = new JButton("End Turn");
	add(endTurn);
	endTurn.setBounds(130,100, 100, 40);
	
	use = new JButton("Use");
	add(use);
	use.setBounds(130,150, 100, 40);
	
	
}

public JButton getAttack() {
	return attack;
}
public JButton getBlock() {
	return block;
}
public JButton getEndTurn() {
	return endTurn;
}

}
