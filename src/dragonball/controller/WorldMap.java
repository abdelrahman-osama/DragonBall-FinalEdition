package dragonball.controller;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.swing.*;

import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.SuperSaiyan;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.battle.BattleEventType;
import dragonball.model.cell.Collectible;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.dragon.Dragon;
import dragonball.model.exceptions.DuplicateAttackException;
import dragonball.model.exceptions.MapIndexOutOfBoundsException;
import dragonball.model.exceptions.MaximumAttacksLearnedException;
import dragonball.model.exceptions.NotASaiyanException;
import dragonball.model.exceptions.NotEnoughAbilityPointsException;
import dragonball.model.game.Game;
import dragonball.model.game.GameListener;
import dragonball.view.View;
import javafx.scene.control.ComboBox;

public class WorldMap extends JFrame implements KeyListener, GameListener, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String j;
	JPanel x;
	Game game;
	JLabel fighter;
	JButton ChangeF;
	JButton CreateF;
	JButton UpgradeF;
	private JPanel iBar;
	private JLabel dB;
	private JLabel sB;
	View view;
	int ctr=0;
	private JLabel fighters;
	private JLabel pName;
	private JLabel fName;
	private JLabel fXP;
	private char Type;
	private JButton HP;
	private JButton BD;
	private JButton PD;
	private JButton Ki;
	private JButton Stamina;
	private JLabel Lvl;
	//private Font NumFont;
	private Font NumFont;
	private BattleView battleField;
	private JButton aAtt1;
	private JButton rAtt1;
	private JButton aAtt2;
	private JButton rAtt2;
	private JComboBox<String> AvA1;
	private JComboBox<String> AsA1;
	private JComboBox<String> AvA2;
	private JComboBox<String> AsA2;
	private Battle b;
	private BattleEvent be;
	private Font WinnerFont;
	private int blevel;
	private Fighter battlefoe;
	private String skill;
	private JLabel collectible;
	private JLabel l;
	
	public WorldMap(Game game, View view) {
		super(view.getTitle());
		this.game=game;
		this.view=view;
		game.setListener(this);
		view.setVisible(false);
		try {
		    //create the font to use. Specify the size!
		    NumFont = Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")).deriveFont(20f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")));
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		
		
		try {
		    WinnerFont = Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")).deriveFont(30f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")));
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		setSize(1366,780);
		x = new JPanel(new GridLayout(10, 10));
		generateMap();
	    setContentPane(new JLabel(new ImageIcon(RandomMap())));
	    setLayout(new BorderLayout());
	    add(x);
	    x.addKeyListener(this);
	    x.setFocusable(true);
	    BottomBar();
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//TESTING INPUT ***********************************************************************************
		game.getPlayer().getSuperAttacks().add(new SuperAttack("Kamehameha", 300));				//		 *
		game.getPlayer().getSuperAttacks().add(new SuperAttack("Abrakadabra", 300));		   //		*
		game.getPlayer().getSuperAttacks().add(new SuperAttack("BoomBoom", 300));			  //	   *
		game.getPlayer().getSuperAttacks().add(new SuperAttack("PewPew", 300));			 	 //	  	  *
		game.getPlayer().getUltimateAttacks().add(new UltimateAttack("HumbaMumba", 300));	//	     *
		game.getPlayer().getUltimateAttacks().add(new UltimateAttack("Java", 300));	 	   //       *
		game.getPlayer().setDragonBalls(6);									        	  //       *
//		*****************************************************************************************
		game.getPlayer().getSuperAttacks().add(new SuperAttack("Super Cannon Jack",100));
		game.getPlayer().getSuperAttacks().add(new SuperAttack("Super Duper Sushi",200));
		//game.getPlayer().getActiveFighter().getSuperAttacks().add(new SuperAttack("Menna",300));
//		game.getPlayer().getActiveFighter().getSuperAttacks().add(new SuperAttack("Super MET",1500));
//		game.getPlayer().getActiveFighter().getUltimateAttacks().add(new UltimateAttack("Ultimate Humba Mumba",1000));
//		game.getPlayer().getActiveFighter().getUltimateAttacks().add(new UltimateAttack("The Ultimate Slim",1000));
		game.getPlayer().getSuperAttacks().add(new SuperAttack("Super MET",600));
		game.getPlayer().getUltimateAttacks().add(new UltimateAttack("Ultimate Humba Mumba",780));
		game.getPlayer().getUltimateAttacks().add(new UltimateAttack("The Ultimate Slim",1000));
		game.getPlayer().getUltimateAttacks().add(new SuperSaiyan());
		
		game.getPlayer().getActiveFighter().setAbilityPoints(20);
		worldInfoUpdater();
		// JMenu, JMenuBar, JMenuItem
		
//		this.addWindowListener(new java.awt.event.WindowAdapter() {
//		    @Override
//		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
////		        if (JOptionPane.showConfirmDialog(this, 
////		            "Are you sure to close this window?", "Really Closing?", 
////		            JOptionPane.YES_NO_OPTION,
////		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
////		            System.exit(0);	
////		        }
//		    	exitDialog();
//		    }
//		});
	}
	private String RandomMap(){
		int k = new Random().nextInt(5);
		
		switch(k){
		case 0: j="Maps/MMaps1.png";break;
		case 1: j="Maps/MMaps2.png";break;
		case 2: j="Maps/MMaps3.png";break;
		case 3: j="Maps/MMaps4.png";break;
		case 4: j="Maps/MMaps5.png";break;
		}
		return j;
	}
	private void generateMap(){

		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				if(i==0&& j==0){
					JLabel boss = new JLabel(new ImageIcon("WorldResources/boss.png"));
					x.add(boss);
				}
				else{
					 l= new JLabel(new ImageIcon(fighterType()));
				if (i == game.getWorld().getPlayerRow() && j == game.getWorld().getPlayerColumn())
					l.setVisible(true);
				else
					l.setVisible(false);
				x.add(l);
				l.setOpaque(false);
			}
			}
		}
		x.setOpaque(false);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyPressed(KeyEvent e) {
		//set panel 3ala this, set focusable 3al panel b true
		int oldLocation=Integer.parseInt(game.getWorld().getPlayerRow()+""+game.getWorld().getPlayerColumn());
		try{
		x.getComponent(oldLocation).setVisible(false);
			switch(e.getKeyCode()){
			case KeyEvent.VK_UP:
				game.getWorld().moveUp();
	            break;
	        case KeyEvent.VK_DOWN:
	        	game.getWorld().moveDown();
	            break;
	        case KeyEvent.VK_RIGHT :
	        	game.getWorld().moveRight();
	            break;
	        case KeyEvent.VK_LEFT:
	        	game.getWorld().moveLeft();
	            break;
	        case KeyEvent.VK_ESCAPE:
	        	exitDialog();
			}
			map_Update_View();
			
		} catch (MapIndexOutOfBoundsException a){
			((JLabel) x.getComponent(oldLocation)).setIcon(new ImageIcon(fighterType()));
			x.getComponent(oldLocation).setVisible(true);
		}
	}
	private void map_Update_View() {
		JLabel b = (JLabel) (x.getComponent(0));
		b.setIcon(new ImageIcon("WorldResources/boss.png"));
		JLabel x = (JLabel) this.x.getComponent(Integer.parseInt(game.getWorld().getPlayerRow()+""+game.getWorld().getPlayerColumn()));
		x.setIcon(new ImageIcon(fighterType()));
		x.setVisible(true);
		revalidate();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDragonCalled(Dragon dragon) {
		DragonMode dragonMode = new DragonMode(game, this, dragon);
		setVisible(false);
		view.getContentPane().removeAll();
		view.add(dragonMode);
		//dragonMode.setVisible(true);
		view.revalidate();
		view.setVisible(true);
	}
	
	public void worldInfoUpdater(){
		sB.setText(game.getPlayer().getSenzuBeans()+"");
		dB.setText(game.getPlayer().getDragonBalls()+"");
	}

	@Override
	public void onCollectibleFound(Collectible collectible) {
		if(collectible == Collectible.SENZU_BEAN){
			sB.setText(""+game.getPlayer().getSenzuBeans());
			
		}
		if(collectible == Collectible.DRAGON_BALL){
			dB.setText(""+game.getPlayer().getDragonBalls());
		}
	}
	@Override
	public void onBattleEvent(BattleEvent e) {
		// TODO Auto-generated method stub
		if(e.getType().equals(BattleEventType.STARTED)){
		 b =(Battle) e.getSource();
		 battlefoe = (Fighter) b.getFoe();
		 battleField = new BattleView(game, this,(Battle) e.getSource());
		setVisible(false);
		view.getContentPane().removeAll();
		view.add(battleField);
		view.revalidate();
		view.setVisible(true);
		blevel = game.getPlayer().getActiveFighter().getLevel();
		skillSearch();
		
		}
		if(e.getType().equals(BattleEventType.ENDED)){
			afterBattle(e);
			InfoPanel();
			map_Update_View();
		}

		battleField.battleListener(e);
		
		
	}
	
	//===================================NEW SKILL SEARCHER=================================
	
	public String skillSearch(){
		skill = null;
		if(!battlefoe.getSuperAttacks().isEmpty()){
		for(int i=0;i<battlefoe.getSuperAttacks().size();i++){
			skill = battlefoe.getSuperAttacks().get(i).getName();
		}
		}
		else if(!battlefoe.getUltimateAttacks().isEmpty()){
		for(int i=0;i<battlefoe.getUltimateAttacks().size();i++){
			skill = battlefoe.getUltimateAttacks().get(i).getName();
		}
		}
		
//		else{
//			skill = "No new skills added";
//		}
		return skill;
	}
	
	
	//===================================AFTER BATTLE=====================================
	public void afterBattle(BattleEvent e){
		JDialog afterBattle = new JDialog(this,"Battle Ended",ModalityType.APPLICATION_MODAL);
		afterBattle.setContentPane(new JLabel(new ImageIcon("DialogResources/DialogBG.png")));
		afterBattle.setUndecorated(true);
		afterBattle.getRootPane().setOpaque(false);
		afterBattle.setLayout(null);
		afterBattle.setSize(600, 600);
		afterBattle.setLocationRelativeTo(null);
		JButton close = new JButton(new ImageIcon("DialogResources/CloseButton.png"));
		afterBattle.add(close);
		close.setBounds(550, 10, 40, 30);
		close.setFocusable(false); 
		close.setBorder(BorderFactory.createEmptyBorder());
	    close.setContentAreaFilled(false);
	    //cUD.add(fighters);
		//close.setVisible(true);
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				afterBattle.dispose();
				view.dispose();
				setVisible(true);
			}
		});
		
		
		JLabel winner = new JLabel("",SwingConstants.CENTER);
		winner.setForeground(Color.WHITE);
		winner.setFont(WinnerFont);
		winner.setBounds(100, 20, 400, 200);
		
		JLabel levelupgrats = new JLabel("",SwingConstants.CENTER);
		levelupgrats.setForeground(Color.WHITE);
		levelupgrats.setFont(WinnerFont);
		levelupgrats.setBounds(100, 80, 400, 200);
		
		JLabel levelup = new JLabel("",SwingConstants.CENTER);
		levelup.setForeground(Color.WHITE);
		levelup.setFont(WinnerFont);
		levelup.setBounds(0, 110, 600, 200);
		
		JLabel newskill = new JLabel("",SwingConstants.CENTER);
		newskill.setForeground(Color.WHITE);
		newskill.setFont(WinnerFont);
		newskill.setBounds(0, 140, 600, 200);
		
//		JLabel newXP = new JLabel("", SwingConstants.CENTER);
//		newXP.setForeground(Color.WHITE);
//		newXP.setFont(WinnerFont);
//		newXP.setBounds(50, 110, 600, 200);
		
		JProgressBar newXP = new JProgressBar();
		newXP.setMinimum(0);
		newXP.setMaximum(game.getPlayer().getActiveFighter().getTargetXp());
		newXP.setStringPainted(true);
		newXP.setForeground(Color.ORANGE);
		newXP.setValue(game.getPlayer().getActiveFighter().getXp());
		newXP.setBounds(150, 380, 300, 32);
		newXP.setOpaque(false);
		newXP.setString("XP");
		
		
		if(e.getWinner().equals(b.getMe())){
			winner.setText("YOU WON, "+ game.getPlayer().getName()+"!");
			newskill.setText("New skills added : " + skillSearch());
		}
		else{
			winner.setText("YOU LOST, "+ game.getPlayer().getName()+"!");
		}
		if(blevel!=game.getPlayer().getActiveFighter().getLevel()){
			levelupgrats.setText("Congratulations!");
			levelup.setText("You leveled up from level " + blevel + " to level " + game.getPlayer().getActiveFighter().getLevel());
			Lvl.setText(game.getPlayer().getActiveFighter().getLevel()+"");
			fXP.setText(game.getPlayer().getActiveFighter().getXp()+"");
		}
		afterBattle.add(newXP);
		afterBattle.add(newskill);
		afterBattle.add(levelupgrats);
		afterBattle.add(levelup);
		afterBattle.add(winner);
		afterBattle.setVisible(true);
		
		
		
	}
	
	public void BottomBar(){
			iBar = new JPanel();
		    add(iBar,BorderLayout.SOUTH);
		    ChangeF = new JButton(new ImageIcon("InfoBar/ChangeFighter.png"));
		    ChangeF.setRolloverIcon(new ImageIcon("InfoBar/ChangeFighterB.png"));
		    ChangeF.setBorder(BorderFactory.createEmptyBorder());
			ChangeF.setContentAreaFilled(false);
			ChangeF.setFocusable(false);
			ChangeF.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					changeFighterDialog();			
				}
			});
		    iBar.setLayout(new FlowLayout());
		    JButton CreateF = new JButton(new ImageIcon("InfoBar/CreateFighter.png"));
		    CreateF.setRolloverIcon(new ImageIcon("InfoBar/CreateFighterB.png"));
		    CreateF.setBorder(BorderFactory.createEmptyBorder());
		    CreateF.setContentAreaFilled(false);
		    CreateF.setFocusable(false);
		    CreateF.addActionListener((ActionEvent e) -> {
		    	setVisible(false);
				MapCreateFighter cF = new MapCreateFighter(this,game);
				view.getContentPane().removeAll();
				view.add(cF);
				view.revalidate();
				view.setVisible(true);
		    });
		   // CreateF.setContentAreaFilled(false);
		    JButton UpgradeF = new JButton(new ImageIcon("InfoBar/UpgradeFighter.png"));
		    UpgradeF.setRolloverIcon(new ImageIcon("InfoBar/UpgradeFighterB.png"));
		    UpgradeF.setBorder(BorderFactory.createEmptyBorder());
		    UpgradeF.setContentAreaFilled(false);
		    UpgradeF.setFocusable(false);
		    UpgradeF.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					upgradeFighter();
					
				}
			});
		    
		    JButton assignAttack = new JButton(new ImageIcon("InfoBar/AssignAttacks.png"));
		    assignAttack.setRolloverIcon(new ImageIcon("InfoBar/AssignAttacksB.png"));
		    assignAttack.setBorder(BorderFactory.createEmptyBorder());
		    assignAttack.setContentAreaFilled(false);
		    assignAttack.setFocusable(false); 
		    assignAttack.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					assignAttackPanel();
					
				}
			});
		    iBar.add(CreateF);
		    iBar.add(ChangeF);
		    iBar.add(UpgradeF);
		    iBar.add(assignAttack);
		    InfoPanel();
		    FighterData();
		    iBar.setOpaque(false);
	}
	public void InfoPanel(){
		JPanel iInfo = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        //JLabel senzuI = new JLabel(new ImageIcon("senzuBean.png"));
        //senzuI.setText(""+game.getPlayer().getSenzuBeans());
        //senzuI.setFont(new java.awt.Font(null,1,20));
  
        pName = new JLabel(new ImageIcon("InfoBar/pName.png"));
        pName.setText(game.getPlayer().getName());
        //pName.setFont(new java.awt.Font(null, 1, 20));
        pName.setFont(fontSetter(game.getPlayer().getName()));
        pName.setForeground(Color.WHITE);
        
        fName = new JLabel(new ImageIcon("InfoBar/fName.png"));
        fName.setText(game.getPlayer().getActiveFighter().getName());
        //fName.setFont(new java.awt.Font(null, 1, 20));
        fName.setFont(fontSetter(game.getPlayer().getActiveFighter().getName()));
        fName.setForeground(Color.WHITE);
        
        fXP = new JLabel(new ImageIcon("InfoBar/fXP.png"));
        fXP.setText(game.getPlayer().getActiveFighter().getXp() + " / " + game.getPlayer().getActiveFighter().getTargetXp());
       // fXP.setFont(new java.awt.Font(null, 1, 20));
        fXP.setFont(fontSetter("0"));
        fXP.setForeground(Color.WHITE);
        
		//iInfo.add(senzuI,gbc);
		iInfo.add(pName,gbc);
		iInfo.add(fName,gbc);
		iInfo.add(fXP,gbc);
			iBar.add(iInfo);
		iInfo.setOpaque(false);
	}
	public void FighterData(){
		JPanel fData = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		Lvl = new JLabel(new ImageIcon("InfoBar/Lvl.png"));
		Lvl.setText(""+game.getPlayer().getActiveFighter().getLevel());
		//Lvl.setFont(new java.awt.Font(null, 1, 20));
		Lvl.setFont(fontSetter("0"));
		Lvl.setForeground(Color.WHITE);
		
		dB = new JLabel(new ImageIcon("InfoBar/dB.png"));
		dB.setText(""+game.getPlayer().getDragonBalls());
		//dB.setFont(new java.awt.Font(null, 1, 20));
		dB.setFont(fontSetter("0"));
		dB.setForeground(Color.WHITE);
		
		sB = new JLabel(new ImageIcon("senzuBean.png"));
		sB.setText(""+game.getPlayer().getSenzuBeans());
		sB.setFont(fontSetter("0"));
		//sB.setFont(new java.awt.Font(null, 1, 20));
		sB.setForeground(Color.WHITE);
		
		fData.add(Lvl,gbc);
		fData.add(dB,gbc);
		fData.add(sB,gbc);
		iBar.add(fData);
		fData.setOpaque(false);
	}
	private void changeFighterDialog(){
		fighters = new JLabel();
		fighters.setBorder(BorderFactory.createEmptyBorder());
		fighters.setBounds(260, 0, 500, 500);
		activeChoose(game.getPlayer().getActiveFighter());
		JDialog cFD = new JDialog(this,"Change Fighter",ModalityType.APPLICATION_MODAL);
		cFD.setContentPane(new JLabel(new ImageIcon("DialogResources/DialogBG.png")));
		cFD.setUndecorated(true);
		cFD.getRootPane().setOpaque(false);
		cFD.setLayout(null);
		cFD.setSize(600, 600);
		cFD.setLocationRelativeTo(null);
		JButton close = new JButton(new ImageIcon("DialogResources/CloseButton.png"));
		cFD.add(close);
		close.setBounds(550, 10, 40, 30);
		close.setBorder(BorderFactory.createEmptyBorder());
		close.setFocusable(false); 
	    close.setContentAreaFilled(false);
	    cFD.add(fighters);
		//close.setVisible(true);
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cFD.dispose();
			}
		});
		
		JLabel FighterName = new JLabel();
		FighterName.setText(game.getPlayer().getFighters().get(ctr).getName());
		FighterName.setFont(new Font(null,1,20));
		FighterName.setForeground(Color.WHITE);
		FighterName.setBounds(270, 350, 600, 100);
		cFD.add(FighterName);
		
		
		//hena ha3mel el arrow control
		JLabel nFighters = new JLabel(ctr+1+ "/"+game.getPlayer().getFighters().size());
		JButton rArrow = new JButton(new ImageIcon("DialogResources/rArrow.png"));
		rArrow.setFocusable(false); 
		cFD.add(rArrow);
		rArrow.setBounds(460, 200, 115, 75);
		rArrow.setBorder(BorderFactory.createEmptyBorder());
		rArrow.setContentAreaFilled(false);
		rArrow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ctr+1<game.getPlayer().getFighters().size()){
					ctr++;
					chooseFighters(ctr);
					cFD.add(fighters);
					nFighters.setText(ctr+1 + "/"+game.getPlayer().getFighters().size());
					FighterName.setText(game.getPlayer().getFighters().get(ctr).getName());
					cFD.repaint();
					cFD.revalidate();
				}
			}
		});
		JButton lArrow = new JButton(new ImageIcon("DialogResources/lArrow.png"));
		cFD.add(lArrow);
		lArrow.setBounds(30, 200, 115, 75);
		lArrow.setFocusable(false); 
		lArrow.setBorder(BorderFactory.createEmptyBorder());
		lArrow.setContentAreaFilled(false);
		lArrow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ctr+1>1){
					ctr--;
					chooseFighters(ctr);
					cFD.add(fighters);
					nFighters.setText(ctr+1 + "/"+game.getPlayer().getFighters().size());
					FighterName.setText(game.getPlayer().getFighters().get(ctr).getName());
					cFD.repaint();
					cFD.revalidate();
				}
			}
		});
		JButton choose = new JButton(new ImageIcon("DialogResources/chooseFighter.png"));
		choose.setRolloverIcon(new ImageIcon("DialogResources/chooseFighterB.png"));
		cFD.add(choose);
		choose.setBounds(170, 480, 250, 85);
		choose.setFocusable(false); 
		choose.setBorder(BorderFactory.createEmptyBorder());
		choose.setContentAreaFilled(false);
		choose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				game.getPlayer().setActiveFighter(game.getPlayer().getFighters().get(ctr));
				fName.setText(game.getPlayer().getActiveFighter().getName());
				map_Update_View();
				cFD.dispose();
			}
		});
		nFighters.setBounds(270, 400, 100, 100);
		nFighters.setForeground(Color.WHITE);
		nFighters.setFont(new java.awt.Font(null, 1, 20));
		
		
		
		
		cFD.add(nFighters);
		cFD.setResizable(false);
		cFD.setVisible(true);
	}
	public void activeChoose(PlayableFighter f){
		chooseFighters(game.getPlayer().getFighters().indexOf(f));
	}
	public void chooseFighters(int i){
			String n =game.getPlayer().getFighters().get(i).getClass().getSimpleName();
			ImageIcon img = null;
			switch(n){
			case "Saiyan" :  img = new ImageIcon("DialogResources/Saiyan.png");break;
			case "Earthling" :	img = new ImageIcon("DialogResources/Earthling.png");break;
			case "Frieza" :	img = new ImageIcon("DialogResources/Frieza.png");break;
			case "Namekian" :	img = new ImageIcon("DialogResources/Namekian.png");break;
			case "Majin" : 	img = new ImageIcon("DialogResources/Majin.png");break;
			}
			fighters.setIcon(img);
	}
	
	public void upgradeFighter(){
		
		JDialog cUD = new JDialog(this,"Upgrade Fighter",ModalityType.APPLICATION_MODAL);
		cUD.setContentPane(new JLabel(new ImageIcon("DialogResources/DialogBG.png")));
		cUD.setUndecorated(true);
		cUD.getRootPane().setOpaque(false);
		cUD.setLayout(null);
		cUD.setSize(600, 600);
		cUD.setLocationRelativeTo(null);
		JButton close = new JButton(new ImageIcon("DialogResources/CloseButton.png"));
		cUD.add(close);
		close.setBounds(550, 10, 40, 30);
		close.setFocusable(false); 
		close.setBorder(BorderFactory.createEmptyBorder());
	    close.setContentAreaFilled(false);
	    //cUD.add(fighters);
		//close.setVisible(true);
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cUD.dispose();
			}
		});

		HP = new JButton(new ImageIcon("upgradeFighterResources/HP.png"));
		HP.setRolloverIcon(new ImageIcon("upgradeFighterResources/HPb.png"));
		cUD.add(HP);
		HP.setBounds(160, 130, 250, 85);
		HP.setBorder(BorderFactory.createEmptyBorder());
		HP.setFocusable(false); 
		HP.setContentAreaFilled(false);
		HP.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CheckChange('H');
				Type = 'H';
			}
		});
		
		BD = new JButton(new ImageIcon("upgradeFighterResources/BD.png"));
		BD.setRolloverIcon(new ImageIcon("upgradeFighterResources/BDb.png"));
		cUD.add(BD);
		BD.setBounds(160, 200, 250, 85);
		BD.setBorder(BorderFactory.createEmptyBorder());
		BD.setFocusable(false); 
		BD.setContentAreaFilled(false);
		BD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CheckChange('B');
				Type = 'B';
			}
		});

		PD = new JButton(new ImageIcon("upgradeFighterResources/PD.png"));
		PD.setRolloverIcon(new ImageIcon("upgradeFighterResources/PDb.png"));
		cUD.add(PD);
		PD.setBounds(160, 270, 250, 85);
		PD.setBorder(BorderFactory.createEmptyBorder());
		PD.setFocusable(false); 
		PD.setContentAreaFilled(false);
		PD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CheckChange('P');
				Type = 'P';		
			}
		});
		
		Ki = new JButton(new ImageIcon("upgradeFighterResources/Ki.png"));
		Ki.setRolloverIcon(new ImageIcon("upgradeFighterResources/Kib.png"));
		cUD.add(Ki);
		Ki.setBounds(115, 340, 340, 85);
		Ki.setBorder(BorderFactory.createEmptyBorder());
		Ki.setFocusable(false); 
		Ki.setContentAreaFilled(false);
		Ki.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CheckChange('K');
				Type = 'K';
				
			}
		});
		
		Stamina = new JButton(new ImageIcon("upgradeFighterResources/Stamina.png"));
		Stamina.setRolloverIcon(new ImageIcon("upgradeFighterResources/Staminab.png"));
		cUD.add(Stamina);
		Stamina.setBounds(160, 410, 250, 85);
		Stamina.setBorder(BorderFactory.createEmptyBorder());
		Stamina.setFocusable(false); 
		Stamina.setContentAreaFilled(false);
		Stamina.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CheckChange('S');
				Type = 'S';
			}
		});
		
		JButton upgrade = new JButton(new ImageIcon("upgradeFighterResources/upgrade.png"));
		upgrade.setRolloverIcon(new ImageIcon("upgradeFighterResources/upgradeb.png"));
		upgrade.setBounds(135, 500, 300, 100);
		upgrade.setBorder(BorderFactory.createEmptyBorder());
		upgrade.setContentAreaFilled(false);
		upgrade.setFocusable(false); 
		upgrade.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(game.getPlayer().getActiveFighter().getAbilityPoints()==0){
						JOptionPane.showMessageDialog(null, "You don't have enough Ability Points!"); 
					}
					else{
					CheckUpgrade(Type);
					cUD.dispose();
					//cUD.revalidate();
					}
				} catch (NotEnoughAbilityPointsException e1) {
					JOptionPane.showMessageDialog(null, "You need to choose an Upgrade!"); 
				}
			}
		});
		
		JLabel HPb = new JLabel(game.getPlayer().getActiveFighter().getMaxHealthPoints()+"");
		HPb.setFont(fontSetter("la2"));
		HPb.setBounds(70, 130, 115, 75);
		HPb.setBorder(BorderFactory.createEmptyBorder());
		HPb.setForeground(Color.WHITE);
		
		JLabel HPa = new JLabel((game.getPlayer().getActiveFighter().getMaxHealthPoints()+50)+"");
		HPa.setBounds(450, 130, 115, 75);
		HPa.setBorder(BorderFactory.createEmptyBorder());
		HPa.setFont(fontSetter("la2"));
		HPa.setForeground(Color.WHITE);

		JLabel BDb = new JLabel(game.getPlayer().getActiveFighter().getBlastDamage()+"");
		BDb.setBounds(70, 200, 115, 75);
		BDb.setBorder(BorderFactory.createEmptyBorder());
		BDb.setForeground(Color.WHITE);
		BDb.setFont(fontSetter("la2"));
		
		JLabel BDa = new JLabel((game.getPlayer().getActiveFighter().getBlastDamage()+50)+"");
		BDa.setBounds(450, 200, 115, 75);
		BDa.setBorder(BorderFactory.createEmptyBorder());
		BDa.setForeground(Color.WHITE);
		BDa.setFont(fontSetter("la2"));
		
		JLabel PDb = new JLabel (game.getPlayer().getActiveFighter().getPhysicalDamage()+"");
		PDb.setBounds(70, 270, 115, 75);
		PDb.setBorder(BorderFactory.createEmptyBorder());
		PDb.setForeground(Color.WHITE);
		PDb.setFont(fontSetter("la2"));
		
		JLabel PDa = new JLabel((game.getPlayer().getActiveFighter().getPhysicalDamage()+50)+"");
		PDa.setBounds(450, 270, 115, 75);
		PDa.setBorder(BorderFactory.createEmptyBorder());
		PDa.setForeground(Color.WHITE);
		PDa.setFont(fontSetter("la2"));
		
		JLabel Kib = new JLabel(game.getPlayer().getActiveFighter().getMaxKi()+"");
		Kib.setBounds(70, 340, 115, 75);
		Kib.setBorder(BorderFactory.createEmptyBorder());
		Kib.setForeground(Color.WHITE);
		Kib.setFont(fontSetter("la2"));
		
		JLabel Kia = new JLabel((game.getPlayer().getActiveFighter().getMaxKi()+1)+"");
		Kia.setBounds(450, 340, 115, 75);
		Kia.setBorder(BorderFactory.createEmptyBorder());
		Kia.setForeground(Color.WHITE);
		Kia.setFont(fontSetter("la2"));
		
		JLabel Stb = new JLabel(game.getPlayer().getActiveFighter().getMaxStamina()+"");
		Stb.setBounds(70, 410, 115, 75);
		Stb.setBorder(BorderFactory.createEmptyBorder());
		Stb.setForeground(Color.WHITE);
		Stb.setFont(fontSetter("la2"));
		
		JLabel Sta = new JLabel((game.getPlayer().getActiveFighter().getMaxStamina()+1)+"");
		Sta.setBounds(450, 410, 115, 75);
		Sta.setBorder(BorderFactory.createEmptyBorder());
		Sta.setForeground(Color.WHITE);
		Sta.setFont(fontSetter("la2"));
		
		
		JLabel abilityP = new JLabel(new ImageIcon("upgradeFighterResources/abilityPoints.png"));
		abilityP.setBounds(250, -22, 300, 200);
		
		JLabel abilityP2 = new JLabel();
		abilityP2.setText(""+game.getPlayer().getActiveFighter().getAbilityPoints());
		abilityP2.setFont(fontSetter("la2"));
		abilityP2.setForeground(Color.WHITE);
		abilityP2.setBounds(490, -23, 300, 200);
		
		JLabel before = new JLabel(new ImageIcon("upgradeFighterResources/before.png"));
		before.setBounds(50, 10, 200, 200);

		JLabel after = new JLabel(new ImageIcon("upgradeFighterResources/after.png"));
		after.setBounds(350, 10, 200, 200);
		
		JLabel chF = new JLabel(new ImageIcon("upgradeFighterResources/upgradeFighter.png"));
		chF.setBounds(100, -50, 400, 200);

		cUD.add(abilityP);
		cUD.add(abilityP2);
		cUD.add(HP);
		cUD.add(BD);
		cUD.add(PD);
		cUD.add(Ki);
		cUD.add(Stamina);
		cUD.add(HPb);
		cUD.add(HPa);
		cUD.add(BDb);
		cUD.add(BDa);
		cUD.add(PDb);
		cUD.add(PDa);
		cUD.add(Kib);
		cUD.add(Kia);
		cUD.add(Stb);
		cUD.add(Sta);
		cUD.add(before);
		cUD.add(after);
		cUD.add(chF);
		cUD.add(upgrade);
		cUD.setVisible(true);
	}
	
	
	@SuppressWarnings("unused")
	private void CheckUpgrade(char c) throws NotEnoughAbilityPointsException {
		
		switch(c){
		case'H': game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'H');break;
			
		case'B': game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'B');break;
			
		case'P': game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'P');break;
			
		case'K': game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'K');break;
			
		case'S': game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'S');break;
		}
		
	}
	
	private void CheckChange(char c) {
		if(c!=Type){
			switch(Type){
			case'H':HP.setIcon(new ImageIcon("upgradeFighterResources/HP.png"));break;
			case'B':BD.setIcon(new ImageIcon("upgradeFighterResources/BD.png"));break;
			case'P':PD.setIcon(new ImageIcon("upgradeFighterResources/PD.png"));break;
			case'K':Ki.setIcon(new ImageIcon("upgradeFighterResources/Ki.png"));break;
			case'S':Stamina.setIcon(new ImageIcon("upgradeFighterResources/Stamina.png"));break;
			
			}
			
		}
		switch(c){
		case'H':HP.setIcon(new ImageIcon("upgradeFighterResources/HPb.png"));break;
		case'B':BD.setIcon(new ImageIcon("upgradeFighterResources/BDb.png"));break;
		case'P':PD.setIcon(new ImageIcon("upgradeFighterResources/PDb.png"));break;
		case'K':Ki.setIcon(new ImageIcon("upgradeFighterResources/Kib.png"));break;
		case'S':Stamina.setIcon(new ImageIcon("upgradeFighterResources/Staminab.png"));break;
		}
		
	}
	
	public void assignAttackPanel(){
		JDialog cAP = new JDialog(this,"Upgrade Fighter",ModalityType.APPLICATION_MODAL);
		cAP.setContentPane(new JLabel(new ImageIcon("DialogResources/DialogBG.png")));
		cAP.setUndecorated(true);
		cAP.getRootPane().setOpaque(false);
		cAP.setLayout(null);
		cAP.setSize(600, 600);
		cAP.setLocationRelativeTo(null);
		JButton close = new JButton(new ImageIcon("DialogResources/CloseButton.png"));
		cAP.add(close);
		JLabel SuperAttacks = new JLabel(new ImageIcon("assignAttackResources/SuperAttacks.png"));
		SuperAttacks.setBounds(150, -70, 300, 300);
		
		JLabel UltimateAttacks = new JLabel(new ImageIcon("assignAttackResources/UltimateAttacks.png"));
		UltimateAttacks.setBounds(130, 150, 350, 300);
		
		JLabel AvAtt1 = new JLabel(new ImageIcon("assignAttackResources/AvailableAttacks.png"));
		AvAtt1.setBounds(-30, 0, 300, 300);

		JLabel AssAtt1 = new JLabel(new ImageIcon("assignAttackResources/AssignedAttacks.png"));
		AssAtt1.setBounds(250, 0, 300, 300);
		
		
		AvA1 = new JComboBox<String>();
		AvA1.addItem("None");
		AvA1.setBounds(20, 176, 200, 30);
		for(int i=0;i<game.getPlayer().getSuperAttacks().size();i++){
			AvA1.addItem(game.getPlayer().getSuperAttacks().get(i).getName()+"");
		}
		
		AsA1 = new JComboBox<String>();
		AsA1.addItem("None");
		AsA1.setBounds(300, 176, 200, 30);
		for(int i=0;i<game.getPlayer().getActiveFighter().getSuperAttacks().size();i++){
			AsA1.addItem(game.getPlayer().getActiveFighter().getSuperAttacks().get(i).getName()+"");
		}
		
		AvA2 = new JComboBox<String>();
		AvA2.addItem("None");
		AvA2.setBounds(20, 410, 200, 30);
		for(int i=0;i<game.getPlayer().getUltimateAttacks().size();i++){
			AvA2.addItem(game.getPlayer().getUltimateAttacks().get(i).getName()+"");
		}
		
		AsA2 = new JComboBox<String>();
		AsA2.addItem("None");
		AsA2.setBounds(300, 410, 200, 30);
		for(int i=0;i<game.getPlayer().getActiveFighter().getUltimateAttacks().size();i++){
			AsA2.addItem(game.getPlayer().getActiveFighter().getUltimateAttacks().get(i).getName()+"");
		}
		
		
		aAtt1 = new JButton(new ImageIcon("assignAttackResources/assignatt.png"));
		aAtt1.setBounds(210, 180, 80, 40);
		aAtt1.setFocusable(false); 
		aAtt1.setBorder(BorderFactory.createEmptyBorder());
		aAtt1.setContentAreaFilled(false);
		aAtt1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(AvA1.getSelectedItem()!="None" && game.getPlayer().getActiveFighter().getSuperAttacks().size()<4){
					SattackSearch(AvA1.getSelectedItem()+"",null);
					AsA1.addItem(AvA1.getSelectedItem()+"");
				}
				else{
					JOptionPane.showMessageDialog(null, "You have the maximum number of attacks!"); 
				}
				
			}
		});
		
		rAtt1 = new JButton(new ImageIcon("assignAttackResources/switch.png"));
		rAtt1.setFocusable(false); 
		rAtt1.setBounds(490, 180, 80, 40);
		rAtt1.setBorder(BorderFactory.createEmptyBorder());
		rAtt1.setContentAreaFilled(false);
		rAtt1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(AvA1.getSelectedItem()!="None" && AsA1.getSelectedItem()!="None"){
					SattackSearch(AvA1.getSelectedItem()+"",AsA1.getSelectedItem()+"");
					AsA1.addItem(AvA1.getSelectedItem()+"");
					AsA1.removeItemAt(AsA1.getSelectedIndex());
				}		
			}
		});
		//========================================================
		aAtt2 = new JButton(new ImageIcon("assignAttackResources/assignatt.png"));
		aAtt2.setBounds(210, 415, 80, 40);
		aAtt2.setFocusable(false); 
		aAtt2.setBorder(BorderFactory.createEmptyBorder());
		aAtt2.setContentAreaFilled(false);
		aAtt2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(AvA2.getSelectedItem()!="None" && game.getPlayer().getActiveFighter().getUltimateAttacks().size()<2){
					UattackSearch(AvA2.getSelectedItem()+"",null);
					AsA2.addItem(AvA2.getSelectedItem()+"");
					
				}
				else{
					JOptionPane.showMessageDialog(null, "You have the maximum number of attacks!"); 
				}
				
			}
		});
		
		
		rAtt2 = new JButton(new ImageIcon("assignAttackResources/switch.png"));
		rAtt2.setBounds(490, 415, 80, 40);
		rAtt1.setFocusable(false); 
		rAtt2.setBorder(BorderFactory.createEmptyBorder());
		rAtt2.setContentAreaFilled(false);
		rAtt2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(AvA2.getSelectedItem()!="None" && AsA2.getSelectedItem()!="None"){
					UattackSearch(AvA2.getSelectedItem()+"",AsA2.getSelectedItem()+"");
					AsA2.addItem(AvA2.getSelectedItem()+"");
					AsA2.removeItemAt(AsA2.getSelectedIndex());
				}
				
			}
		});
		JLabel AvAtt2 = new JLabel(new ImageIcon("assignAttackResources/AvailableAttacks.png"));
		AvAtt2.setBounds(-30, 230, 300, 300);
		
		JLabel AssAtt2 = new JLabel(new ImageIcon("assignAttackResources/AssignedAttacks.png"));
		AssAtt2.setBounds(250, 230, 300, 300);
		
	
		
		//=============================================================================================
		JButton Assign = new JButton(new ImageIcon("assignAttackResources/Assign.png"));
		Assign.setRolloverIcon(new ImageIcon("assignAttackResources/Assignb.png"));
		Assign.setBorder(BorderFactory.createEmptyBorder());
		Assign.setFocusable(false); 
		Assign.setContentAreaFilled(false);
		Assign.setBounds(140, 500, 300, 80);
		Assign.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cAP.dispose();
			}
		});
	

		
		
		cAP.add(SuperAttacks);
		cAP.add(UltimateAttacks);
		cAP.add(AvAtt1);
		cAP.add(AssAtt1);
		cAP.add(AvAtt2);
		cAP.add(AssAtt2);
		cAP.add(rAtt1);
		cAP.add(rAtt2);
		cAP.add(AvA1);
		cAP.add(AsA1);
		cAP.add(AvA2);
		cAP.add(AsA2);
		cAP.add(aAtt1);
		cAP.add(aAtt2);
		cAP.add(Assign);
		cAP.setVisible(true);
	}
	
	
	private void SattackSearch(String newAttack,String oldAttack){
		SuperAttack old = null;
		if(oldAttack == null){
		for(int i=0;i<game.getPlayer().getSuperAttacks().size();i++){
			if(newAttack.equals(game.getPlayer().getSuperAttacks().get(i).getName())){//shof elnull tany
					try {
						game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), game.getPlayer().getSuperAttacks().get(i), null);
//						SattackSearch(AvA1.getSelectedItem()+"",null);
//						AsA1.addItem(AvA1.getSelectedItem()+"");
					} catch (MaximumAttacksLearnedException | DuplicateAttackException | NotASaiyanException e) {
						JOptionPane.showMessageDialog(null, "You didn't write something in this exception yet (SAttackSearch)"); 
						e.printStackTrace();
					}
				
				//game.getPlayer().getActiveFighter().getSuperAttacks().add(game.getPlayer().getSuperAttacks().get(i));
			}
		}
		//game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), newAttack, null);
		}
		else{
			for(int j=0;j<game.getPlayer().getActiveFighter().getSuperAttacks().size();j++){
				if(oldAttack.equals(game.getPlayer().getActiveFighter().getSuperAttacks().get(j).getName())){
					old = game.getPlayer().getActiveFighter().getSuperAttacks().get(j);
				}
			}

			for(int i=0;i<game.getPlayer().getSuperAttacks().size();i++){
				if(newAttack.equals(game.getPlayer().getSuperAttacks().get(i).getName())){//shof elnull tany
					try {
						game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), game.getPlayer().getSuperAttacks().get(i), old);	
					} catch (MaximumAttacksLearnedException e) {
						JOptionPane.showMessageDialog(null, "You already got the maximum number of ultimate attacks allowed!"); 
					} catch (DuplicateAttackException e) {
						JOptionPane.showMessageDialog(null, "You already have this attack assigned!"); 
					} catch (NotASaiyanException e) {
						JOptionPane.showMessageDialog(null, "Only Saiyans can use this attack."); 
					}
					//game.getPlayer().getActiveFighter().getSuperAttacks().add(game.getPlayer().getSuperAttacks().get(i));
				}
			}
			//game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), newAttack, null);
			
			
			
		}
	}
	
	private void UattackSearch(String newAttack,String oldAttack){
//		for(int i=0;i<game.getPlayer().getUltimateAttacks().size();i++){
//			if(newAttack == game.getPlayer().getUltimateAttacks().get(i).getName() && oldAttack=="None"){//shof elnull tany
//				game.getPlayer().getActiveFighter().getUltimateAttacks().add(game.getPlayer().getUltimateAttacks().get(i));
//			}
//		}

		UltimateAttack old = null;
		if(oldAttack == null){
		for(int i=0;i<game.getPlayer().getUltimateAttacks().size();i++){
			if(newAttack.equals(game.getPlayer().getUltimateAttacks().get(i).getName())){//shof elnull tany
					try {
						game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), game.getPlayer().getUltimateAttacks().get(i), null);
						System.out.println("sha3'ala bta3t el ultimate");
					} catch (MaximumAttacksLearnedException | DuplicateAttackException | NotASaiyanException e) {
						JOptionPane.showMessageDialog(null, "You didn't write something in this exception yet (SAttackSearch)"); 
						e.printStackTrace();
					}
				
				//game.getPlayer().getActiveFighter().getSuperAttacks().add(game.getPlayer().getSuperAttacks().get(i));
			}
		}
		//game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), newAttack, null);
		}
		else{
			for(int j=0;j<game.getPlayer().getActiveFighter().getUltimateAttacks().size();j++){
				if(oldAttack.equals(game.getPlayer().getActiveFighter().getUltimateAttacks().get(j).getName())){
					old = game.getPlayer().getActiveFighter().getUltimateAttacks().get(j);
				}
			}

			for(int i=0;i<game.getPlayer().getUltimateAttacks().size();i++){
				if(newAttack.equals(game.getPlayer().getUltimateAttacks().get(i).getName())){//shof elnull tany
					try {
						game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), game.getPlayer().getUltimateAttacks().get(i), old);	
					} catch (MaximumAttacksLearnedException e) {
						JOptionPane.showMessageDialog(null, "You already got the maximum number of ultimate attacks allowed!"); 
					} catch (DuplicateAttackException e) {
						JOptionPane.showMessageDialog(null, "You already have this attack assigned!"); 
					} catch (NotASaiyanException e) {
						JOptionPane.showMessageDialog(null, "Only Saiyans can use this attack."); 
					}
					//game.getPlayer().getActiveFighter().getSuperAttacks().add(game.getPlayer().getSuperAttacks().get(i));
				}
			}
			//game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), newAttack, null);
			
			
			
		}
	
		
	}
	
	
	private Font fontSetter(String attack){
		Font DynFont = null;
		if(attack == "Collectible"){
			try {
			    //create the font to use. Specify the size!
			    DynFont = Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")).deriveFont(50f);
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    //register the font
			    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")));
			} catch (IOException e) {
			    e.printStackTrace();
			} catch(FontFormatException e) {
			    e.printStackTrace();
			}
		}
		else if(attack.length()<=15){
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
	
	public String fighterType(){
		char t =game.getPlayer().getActiveFighter().getClass().getSimpleName().charAt(0);
		String z = null;
		switch(t){
		case 'S' : 	z = "MapFighters/Saiyan.gif"; break;
		case 'E' : 	z = "MapFighters/Earthling.gif"; break;
		case 'M' : 	z = "MapFighters/Majin.gif"; break;
		case 'N' : 	z = "MapFighters/Namekian.gif"; break;
		case 'F' : 	z = "MapFighters/Frieza.gif"; break;	
		}		
		return z;
	}
	
	
	public void exitDialog(){
		JDialog exitDialog = new JDialog(this,"Exit",ModalityType.APPLICATION_MODAL);
		exitDialog.setContentPane(new JLabel(new ImageIcon("DialogResources/DialogBG.png")));
		exitDialog.setUndecorated(true);
		exitDialog.getRootPane().setOpaque(false);
		exitDialog.setLayout(null);
		exitDialog.setSize(580, 220);
		exitDialog.setLocationRelativeTo(null);
		JButton exitWS = new JButton(new ImageIcon("exitDialog/SaveA.png"));
		exitWS.setRolloverIcon(new ImageIcon("exitDialog/SaveB.png"));
		exitWS.setBounds(50, 120, 180, 100);
		exitWS.setFocusable(false);
		exitWS.setBorder(BorderFactory.createEmptyBorder());
		exitWS.setContentAreaFilled(false);
		exitWS.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					game.save("lastestSave.txt");
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JButton exit = new JButton(new ImageIcon("exitDialog/ExitA.png"));
		exit.setRolloverIcon(new ImageIcon("exitDialog/ExitB.png"));
		exit.setBounds(260, 120, 100, 100);
		exit.setFocusable(false);
		exit.setBorder(BorderFactory.createEmptyBorder());
		exit.setContentAreaFilled(false);
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		JButton Cancel = new JButton(new ImageIcon("exitDialog/CancelA.png"));
		Cancel.setRolloverIcon(new ImageIcon("exitDialog/CancelB.png"));
		Cancel.setBounds(390, 120, 120, 100);
		Cancel.setFocusable(false);
		Cancel.setBorder(BorderFactory.createEmptyBorder());
		Cancel.setContentAreaFilled(false);
		Cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitDialog.dispose();
			}
		});
		JLabel msg = new JLabel(new ImageIcon("exitDialog/exitmsg.png"));
		msg.setBounds(90, 30, 400, 100);
		exitDialog.add(exitWS);
		exitDialog.add(exit);
		exitDialog.add(Cancel);
		exitDialog.add(msg);
		exitDialog.setVisible(true);
	}
	
	
}