package dragonball.controller;

import javax.imageio.ImageIO;

import javax.swing.*;
import com.sun.accessibility.internal.resources.accessibility;

import dragonball.model.attack.Attack;
import dragonball.model.attack.MaximumCharge;
import dragonball.model.attack.PhysicalAttack;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.SuperSaiyan;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.battle.BattleEventType;
import dragonball.model.battle.BattleOpponent;
import dragonball.model.cell.Collectible;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.exceptions.AlreadyTransformedException;
import dragonball.model.exceptions.NotEnoughKiException;
import dragonball.model.exceptions.NotEnoughSenzuBeansException;
import dragonball.model.game.Game;

import java.awt.*;
import java.util.*;
import java.util.Timer;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.concurrent.TimeUnit;

public class BattleView extends JLabel{
	JButton attack;
	JButton block;
	JButton use;
	JLabel senzuBeans;
	JLabel kiPointL;
	JButton senzu;
	private Font BigFont;
	private JLabel num;
	private int counter;
	private JLabel tt;
	private Fighter me;
	private Fighter foe;
	private Battle battle;
	private JLabel sal2;
	private JLabel sal1;
	private JLabel sal3;
	private JLabel sal4;
	private JLabel sul;
	private JLabel sul2;
	private JButton sab1;
	private JButton sab2;
	private JButton sab3;
	private AbstractButton sab4;
	private JButton sub;
	private JButton sub2;
	private JProgressBar br;
	private JProgressBar HPPB;
	private JProgressBar opHPPB;
	private JProgressBar KiPB;
	private JProgressBar StPB;
	private JProgressBar opKiPB;
	private JProgressBar opStPB;
	private JDialog cUD;
	private Game game;
	private JLabel turn;
	private JLabel meF;
	private JLabel foeF;
	private char wA;
	private ImageIcon[][] fighterImgs;
	public BattleView(Game game, WorldMap worldMap, Battle battle){
	super(new ImageIcon("BattleResources/BGT.png"));
	this.game=game;
	loadFiles();
	setSize(1366,780);
	 me = (Fighter) battle.getMe();
	 foe = (Fighter) battle.getFoe();
	 this.battle=battle;
	try {
	    BigFont = Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")).deriveFont(30f);
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    //register the font
	    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Adobe-Garamond-Pro-Bold.ttf")));
	} catch (IOException e) {
	    e.printStackTrace();
	} catch(FontFormatException e) {
	    e.printStackTrace();
	}
	upperPanel();
	block = new JButton(new ImageIcon("BattleResources/FBlock.png"));
	block.setRolloverIcon(new ImageIcon("BattleResources/FBlockGlow.png"));
	add(block);
	block.setBounds(750, 630, 180, 70);
	block.setFocusable(false); 
	block.setBorder(BorderFactory.createEmptyBorder());
	block.setContentAreaFilled(false);
	block.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			battle.block();
		}
	});
	senzuBeans = new JLabel(game.getPlayer().getSenzuBeans()+"");
	senzuBeans.setBounds(660, 360, 500, 500);
	senzuBeans.setFont(BigFont);
	senzuBeans.setForeground(Color.WHITE);
	add(senzuBeans);
	
	senzu = new JButton(new ImageIcon("BattleResources/senzuBenaya.png"));
	senzu.setBounds(640, 510, 80, 75);
	senzu.setBorder(BorderFactory.createEmptyBorder());
	senzu.setFocusable(false); 
	senzu.setContentAreaFilled(false);
	add(senzu);
	senzu.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				battle.use(game.getPlayer(), Collectible.SENZU_BEAN);
				senzuBeans.setText(game.getPlayer().getSenzuBeans()+"");
			} catch (NotEnoughSenzuBeansException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "You don't have enough Senzu Beans!"); 
			}
			
		}
	});
	
	
	attack = new JButton(new ImageIcon("BattleResources/FAttack.png"));
	attack.setRolloverIcon(new ImageIcon("BattleResources/FAttackGlow.png"));
	add(attack);
	attack.setBounds(410, 630, 180, 70);
	attack.setFocusable(false); 
	attack.setBorder(BorderFactory.createEmptyBorder());
	attack.setContentAreaFilled(false);
	attack.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			attackPnl();
		}
	});

	kiPointL = new JLabel(new ImageIcon("BattleResources/kiPointL.png"));
	kiPointL.setBounds(140, 80, 50, 50);
	kiPointL.setBorder(BorderFactory.createEmptyBorder());
	add(kiPointL);
	kiPointL.setVisible(false);
	JLabel name = new JLabel(me.getName());
	name.setBounds(100, 85, 400, 100);
	name.setBorder(BorderFactory.createEmptyBorder());
	name.setFont(fontSetter(me.getName()));
	name.setForeground(Color.WHITE);
	
	JLabel level = new JLabel("Level: "+me.getLevel());
	level.setBounds(100, 105, 500, 100);
	level.setBorder(BorderFactory.createEmptyBorder());
	level.setForeground(Color.WHITE);
	level.setFont(fontSetter("pInfo"));
	
	
	
	JLabel opName = new JLabel(foe.getName(),SwingConstants.RIGHT);
	opName.setBounds(868, 85, 400, 100);
	opName.setBorder(BorderFactory.createEmptyBorder());
	opName.setFont(fontSetter(me.getName()));
	opName.setForeground(Color.WHITE);
	
	
	JLabel opLevel = new JLabel("Level: " + foe.getLevel()+"");
	opLevel.setBounds(1200, 105, 500, 100);
	opLevel.setBorder(BorderFactory.createEmptyBorder());
	opLevel.setForeground(Color.WHITE);
	opLevel.setFont(fontSetter("pInfo"));
	
	
	turn = new JLabel();
	turn.setBounds(600, 105, 500, 100);
	turn.setBorder(BorderFactory.createEmptyBorder());
	turn.setForeground(Color.WHITE);
	turn.setFont(fontSetter("pInfo"));

	
	
	initialGif();
	add(turn);
	add(opName);
	add(opLevel);
	add(name);
	add(level);
	setVisible(true);
	
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
	
	public void attackPnl(){
		
		cUD = new JDialog(null,"Attack",ModalityType.APPLICATION_MODAL);
		cUD.setContentPane(new JLabel(new ImageIcon("BattleResources/DialogBG.png")));
		cUD.setUndecorated(true);
		cUD.getRootPane().setOpaque(false);
		cUD.setLayout(null);
		cUD.setSize(850, 270);
		cUD.setLocationRelativeTo(null);
		JButton close = new JButton(new ImageIcon("DialogResources/CloseButton.png"));
		cUD.add(close);
		close.setBounds(800, 10, 40, 30);
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
		
		cUD.setLocation(270, 550);
	
		
		//JButton phys = new JButton(new ImageIcon("BattleResources/PA.png"));
		//phys.setRolloverIcon(new ImageIcon("BattleResources/PAb.png"));
		//phys.setBounds(30, 90, 250, 100);
		//cUD.add(phys);
		//phys.setBorder(BorderFactory.createEmptyBorder());
		//phys.setContentAreaFilled(false);
		
		//============================================================================
		JButton phys = new JButton(new ImageIcon("BattleResources/GreenA.png"));
		phys.setRolloverIcon(new ImageIcon("BattleResources/GreenB.png"));
		phys.setBounds(295, 0, 250, 100);
		phys.setFocusable(false); 
		//phys.setText("Physical Attack");
		//phys.setFont(customFont);
		//phys.setForeground(Color.WHITE);
		phys.setBorder(BorderFactory.createEmptyBorder());
		phys.setContentAreaFilled(false);
		phys.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					battle.attack(new PhysicalAttack());
					wA='P';
					cUD.dispose();
				} catch (NotEnoughKiException e1) {
					JOptionPane.showMessageDialog(null, "You don't have enough Ki!"); 
				} catch (AlreadyTransformedException e1) {
					JOptionPane.showMessageDialog(null, "You are already transformed!"); 
				}
			}
		});
		
		JLabel phys1 = new JLabel("Physical Attack",SwingConstants.CENTER);
		phys1.setBounds(295, 22, 250, 50);
		phys1.setFont(fontSetter("Physical Attack"));
		phys1.setForeground(Color.WHITE);
		
		cUD.add(phys1);
		cUD.add(phys);
		//==============================================================================
		// SUPER ATTACKS
		//==============================================================================
		
		
		sab1 = new JButton(new ImageIcon("BattleResources/RedA.png"));
		sab1.setRolloverIcon(new ImageIcon("BattleResources/RedB.png"));
		sab1.setBounds(30, 70, 250, 100);
		sab1.setBorder(BorderFactory.createEmptyBorder());
		sab1.setFocusable(false); 
		sab1.setContentAreaFilled(false);
		
		sal1 = new JLabel("None", SwingConstants.CENTER);
		sal1.setBounds(30, 92, 250, 50);
		sal1.setFont(fontSetter("Super Kamehameha Cannon"));
		sal1.setFocusable(false); 
		sal1.setForeground(Color.WHITE);
		cUD.add(sal1);
		cUD.add(sab1);
		
		
		//==============================================================================
		
		sab2 = new JButton(new ImageIcon("BattleResources/RedA.png"));
		sab2.setRolloverIcon(new ImageIcon("BattleResources/RedB.png"));
		sab2.setBounds(30, 150, 250, 100);
		sab2.setBorder(BorderFactory.createEmptyBorder());
		sab2.setFocusable(false); 
		sab2.setContentAreaFilled(false);
		
		sal2 = new JLabel("None", SwingConstants.CENTER);
		sal2.setBounds(30, 172, 250, 50);
		sal2.setFont(fontSetter("Super Kamehameha Cannon"));
		sal2.setForeground(Color.WHITE);
		sal2.setFocusable(false); 
		cUD.add(sal2);
		cUD.add(sab2);
		
		
		//==============================================================================
		sab3 = new JButton(new ImageIcon("BattleResources/RedA.png"));
		sab3.setRolloverIcon(new ImageIcon("BattleResources/RedB.png"));
		sab3.setBounds(295, 70, 250, 100);
		sab3.setBorder(BorderFactory.createEmptyBorder());
		sab3.setFocusable(false); 
		sab3.setContentAreaFilled(false);
		
		sal3 = new JLabel("None",SwingConstants.CENTER);
		sal3.setBounds(295, 92, 250, 50);
		sal3.setFont(fontSetter("Physical Attack"));
		sal3.setForeground(Color.WHITE);
		sal3.setFocusable(false); 
		cUD.add(sal3);
		cUD.add(sab3);
		//==============================================================================
		sab4 = new JButton(new ImageIcon("BattleResources/RedA.png"));
		sab4.setRolloverIcon(new ImageIcon("BattleResources/RedB.png"));
		sab4.setBounds(295, 150, 250, 100);
		sab4.setBorder(BorderFactory.createEmptyBorder());
		sab4.setFocusable(false); 
		sab4.setContentAreaFilled(false);
		
		sal4 = new JLabel("None",SwingConstants.CENTER);
		sal4.setBounds(295, 172, 250, 50);
		sal4.setFont(fontSetter("Physical Attack"));
		sal4.setForeground(Color.WHITE);
		sal4.setFocusable(false); 
		cUD.add(sal4);
		cUD.add(sab4);
		//==============================================================================
		// ULTIMATE ATTACKS
		//==============================================================================
		sub = new JButton(new ImageIcon("BattleResources/DarkBlueA.png"));
		sub.setRolloverIcon(new ImageIcon("BattleResources/DarkBlueB.png"));
		sub.setBounds(560, 70, 250, 100);
		sub.setBorder(BorderFactory.createEmptyBorder());
		sub.setFocusable(false); 
		sub.setContentAreaFilled(false);
		
		sul = new JLabel("None",SwingConstants.CENTER);
		sul.setBounds(560, 92, 250, 50);
		sul.setFont(fontSetter("Physical Attack"));
		sul.setForeground(Color.WHITE);
		sul.setFocusable(false); 
		cUD.add(sul);
		cUD.add(sub);
		//==============================================================================
		sub2 = new JButton(new ImageIcon("BattleResources/DarkBlueA.png"));
		sub2.setRolloverIcon(new ImageIcon("BattleResources/DarkBlueB.png"));
		sub2.setBounds(560, 150, 250, 100);
		sub2.setBorder(BorderFactory.createEmptyBorder());
		sub2.setFocusable(false); 
		sub2.setContentAreaFilled(false);
		
		sul2 = new JLabel("None",SwingConstants.CENTER);
		sul2.setBounds(560, 172, 250, 50);
		sul2.setFont(fontSetter("Physical Attack"));
		sul2.setFocusable(false); 
		sul2.setForeground(Color.WHITE);
		cUD.add(sul2);
		cUD.add(sub2);
		//==============================================================================

		
		
		
		superAttackAdder();
		ultimateAttackAdder();
		cUD.setResizable(false);
		cUD.setVisible(true);		
	}
	public void numberIncrementer(JProgressBar jp, int oldValue, int newValue){
		int minValue=0;
		int maxValue=100;
		counter=0;
        Thread runner = new Thread() {
          public void run() {
            counter = minValue;
            while (counter <= maxValue) {
              Runnable runme = new Runnable() {
                public void run() {
                  tt.setText(counter+"");
                }
              };
              SwingUtilities.invokeLater(runme);
              counter++;
              try {
                Thread.sleep(100);
              } catch (Exception ex) {
              }
            }
          }
        };
        runner.start(); 
	}
	public void PBDecrementer(JProgressBar jp, int oldValue, int newValue){
		br = null;
		if(jp==HPPB)
			 br=HPPB;
		else if(jp==StPB)
			br = StPB;
		else if(jp==KiPB)
			br = KiPB;
		else if(jp==opHPPB)
			br=opHPPB;
		else if(jp==opStPB)
			br=opStPB;
		else if(jp==opKiPB)
			br=opKiPB;
		if(oldValue<newValue){
	        Thread runner = new Thread() {
	            public void run() {
	              counter = oldValue;
	              while (counter < newValue) {
	                Runnable runme = new Runnable() {
	                  public void run() {
	                	  br.setValue(counter);
	                  }
	                };
	                SwingUtilities.invokeLater(runme);
	                counter++;
	                try {
	                  Thread.sleep(100);
	                } catch (Exception ex) {
	                }
	              }
	            }
	          };
	          runner.start();
		} else {
			 Thread runner = new Thread() {
		            public void run() {
		              counter = oldValue;
		              while (counter > newValue) {
		                Runnable runme = new Runnable() {
		                  public void run() {
		                	 br.setValue(counter);
		                	  System.out.println(br.getValue()+"l1");
		                  }
		                };
		                SwingUtilities.invokeLater(runme);
		                counter--;
		                try {
		                  Thread.sleep(100);
		                } catch (Exception ex) {
		                }
		              }
		            }
		          };
		          runner.start();
		}
	}
	//===========================PROGRESS BARS==============================
	public void upperPanel(){
		HPPB = new JProgressBar();
		HPPB.setMinimum(0);
		HPPB.setMaximum(me.getMaxHealthPoints());
		HPPB.setStringPainted(true);
		HPPB.setForeground(Color.ORANGE);
		HPPB.setValue(me.getHealthPoints());
		HPPB.setBounds(100, 49, 469, 32);
		HPPB.setOpaque(false);
		HPPB.setString("HP");
		add(HPPB);

		KiPB = new JProgressBar();
		KiPB.setMinimum(0);
		KiPB.setMaximum(me.getMaxKi());
		KiPB.setStringPainted(true);
		KiPB.setForeground(Color.ORANGE);
		KiPB.setValue(me.getKi());
		KiPB.setBounds(102, 88, 365, 14);
		KiPB.setOpaque(false);
		KiPB.setString("Ki");
		add(KiPB);
		
		StPB = new JProgressBar();
		StPB.setMinimum(0);
		StPB.setMaximum(me.getMaxStamina());
		StPB.setStringPainted(true);
		StPB.setForeground(Color.ORANGE);
		StPB.setValue(me.getStamina());
		StPB.setBounds(102, 109, 248, 11);
		StPB.setOpaque(false);
		StPB.setString("Stamina");
		add(StPB);
		//=====================================================================================
		opHPPB = new JProgressBar();
		opHPPB.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		opHPPB.setMinimum(0);
		opHPPB.setMaximum(foe.getMaxHealthPoints());
		opHPPB.setStringPainted(true);
		opHPPB.setForeground(Color.ORANGE);
		opHPPB.setValue(foe.getHealthPoints());
		opHPPB.setBounds(798, 49, 469, 32);
		opHPPB.setOpaque(false);
		opHPPB.setString("HP");
		add(opHPPB);
		
		opKiPB = new JProgressBar();
		opKiPB.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		opKiPB.setMinimum(0);
		opKiPB.setMaximum(foe.getMaxKi());
		opKiPB.setStringPainted(true);
		opKiPB.setForeground(Color.ORANGE);
		opKiPB.setValue(foe.getKi());
		opKiPB.setBounds(898, 88, 365, 14);
		opKiPB.setOpaque(false);
		opKiPB.setString("Ki");
		add(opKiPB);
		
		opStPB = new JProgressBar();
		opStPB.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		opStPB.setMinimum(0);
		opStPB.setMaximum(foe.getMaxStamina());
		opStPB.setStringPainted(true);
		opStPB.setForeground(Color.ORANGE);
		opStPB.setValue(foe.getStamina());
		opStPB.setBounds(1014, 109, 248, 11);
		opStPB.setOpaque(false);
		opStPB.setString("Stamina");
		add(opStPB);
	}
	public void superAttackAdder(){
		
		if(!me.getSuperAttacks().isEmpty()){
		for(int i=0;i<me.getSuperAttacks().size();i++){
			if(i==0){
				sal1.setText(me.getSuperAttacks().get(i).getName()+"");
				sal1.setFont(fontSetter(me.getSuperAttacks().get(i).getName()+""));
				sab1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						try {
							battle.attack(me.getSuperAttacks().get(0));
							wA='S';
							cUD.dispose();
						} catch (NotEnoughKiException e) {
							JOptionPane.showMessageDialog(null, "You don't have enough Ki!"); 
						} catch (AlreadyTransformedException e) {
							JOptionPane.showMessageDialog(null, "You already tansformed master!"); 
						}
						
					}
				});
			}
			else if(i==1){
				sal2.setText(me.getSuperAttacks().get(i).getName()+"");
				sal2.setFont(fontSetter(me.getSuperAttacks().get(i).getName()+""));
				sab2.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						try {
							battle.attack(me.getSuperAttacks().get(1));
							wA='S';
							cUD.dispose();
						} catch (NotEnoughKiException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "You don't have enough Ki!"); 
						} catch (AlreadyTransformedException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "You already tansformed master!"); 
						}
						
					}
				});
			}
			else if(i==2){
				sal3.setText(me.getSuperAttacks().get(i).getName()+"");
				sal3.setFont(fontSetter(me.getSuperAttacks().get(i).getName()+""));
				sab3.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							battle.attack(me.getSuperAttacks().get(2));
							wA='S';
							cUD.dispose();
						} catch (NotEnoughKiException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "You don't have enough Ki!"); 
						} catch (AlreadyTransformedException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "You already tansformed master!"); 
						}
						
						
					}
				});
			}
			else if(i==3){
				sal4.setText(me.getSuperAttacks().get(i).getName()+"");
				sal4.setFont(fontSetter(me.getSuperAttacks().get(i).getName()+""));
				sab4.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							battle.attack(me.getSuperAttacks().get(3));
							wA='S';
							cUD.dispose();
						} catch (NotEnoughKiException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "You don't have enough Ki!"); 
						} catch (AlreadyTransformedException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "You already tansformed master!"); 
						}
						
						
					}
				});
			}
		}
	}
		
	}
	
public void ultimateAttackAdder(){
		
		if(!me.getUltimateAttacks().isEmpty()){
		for(int i=0;i<me.getUltimateAttacks().size();i++){
			if(i==0){
				sul.setText(me.getUltimateAttacks().get(i).getName()+"");
				sul.setFont(fontSetter(me.getUltimateAttacks().get(i).getName()+""));
				sub.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							battle.attack(me.getUltimateAttacks().get(0));
							wA='U';
							cUD.dispose();
						} catch (NotEnoughKiException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "You don't have enough Ki!"); 
						} catch (AlreadyTransformedException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "You already tansformed master!"); 
						}
						
					}
				});
			}
			else if(i==1){
				sul2.setText(me.getUltimateAttacks().get(i).getName()+"");
				sul2.setFont(fontSetter(me.getUltimateAttacks().get(i).getName()+""));
				sub2.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							battle.attack(me.getUltimateAttacks().get(1));
							wA='U';
							cUD.dispose();
						} catch (NotEnoughKiException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "You don't have enough Ki!"); 
						} catch (AlreadyTransformedException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "You already tansformed master!"); 
						}
						
					}
				});
			}
			
			
		}
	}
		
	}

public void battleListener(BattleEvent e) {
	// TODO Auto-generated method stub
	if(e.getType().equals(BattleEventType.BLOCK)){
		blockgif();		
	}
	if(e.getType().equals(BattleEventType.ATTACK)){
		//System.out.println("ana bagy hena");
		if(me.equals(battle.getAttacker())){
			turn.setText(me.getName() + "'s Turn");
			//System.out.println("my turn");
			meF.setIcon(Attacksgif(e.getAttack()));
			//foeF.setIcon(new ImageIcon(""));
		} else
			foeF.setIcon(Attacksgif(e.getAttack()));
	}
	if(e.getType().equals(BattleEventType.NEW_TURN)){
		HPPB.setValue(me.getHealthPoints());
		opHPPB.setValue(foe.getHealthPoints());
		KiPB.setValue(me.getKi());
		opKiPB.setValue(foe.getKi());
		StPB.setValue(me.getStamina());
		opStPB.setValue(foe.getStamina());
		timer(3,((Battle)e.getSource()));
		if(me.equals(battle.getAttacker())){
			turn.setText(me.getName() + "'s Turn");
		}else if(((Battle)e.getSource()).getAttacker().equals(((Battle)e.getSource()).getFoe())){
			turn.setText(foe.getName()+"'s Turn");
			((Battle)e.getSource()).play();
		}
	}
	
}
public void loadFiles(){
	int E = 0, M=0, F = 0, N = 0, S = 0, T = 0,U = 0;
	String path="BattleF/";
	fighterImgs = new ImageIcon[7][8];
	File[] images = fileList("./BattleF",".gif");
	for(int i =0;i < images.length;i++ ){
		String FileName = images[i].getName();
		switch(FileName.charAt(0)){
		case 'E':fighterImgs[0][E] = new ImageIcon(path+FileName);E++;break;
		case 'M':fighterImgs[1][M] = new ImageIcon(path+FileName);M++;break;
		case 'F':fighterImgs[2][F] = new ImageIcon(path+FileName);F++;break;
		case 'N':fighterImgs[3][N] = new ImageIcon(path+FileName);N++;break;
		case 'S':fighterImgs[4][S] = new ImageIcon(path+FileName);S++;break;
		case 'T':fighterImgs[5][T] = new ImageIcon(path+FileName);T++;break;
		case 'U':fighterImgs[6][U] = new ImageIcon(path+FileName);U++;break;
		}
	}
}
public void blockgif(){
	int no =imgNo();
	if(battle.getAttacker().equals(me))
		meF.setIcon(fighterImgs[no][1]);
	else
		foeF.setIcon(fighterImgs[no][1]);
}
public ImageIcon Attacksgif(Attack a){
	int N = imgNo();
	ImageIcon img = null;
	switch(a.getClass().getSimpleName()){
	case "MaximumCharge":img=fighterImgs[N][2];break;
	case "PhysicalAttack":img=fighterImgs[N][3];break;
	case "SuperAttack":img=fighterImgs[N][4];break;
	case "UltimateAttack":
		if(game.getPlayer().getActiveFighter() instanceof Saiyan)
			img=fighterImgs[N][7]; 
		else
			img=fighterImgs[N][5]; 
		break;
	case "SuperSaiyan":img=fighterImgs[N][5];break;
	}
	return img;
}
public int imgNo(){
	int N = 0;
	PlayableFighter f = null;
	NonPlayableFighter foe = null;
	if(battle.getAttacker().equals(game.getPlayer().getActiveFighter()))
		f=game.getPlayer().getActiveFighter();
	else{
		foe=(NonPlayableFighter) battle.getAttacker();
		if(foe.isStrong())
			return 6;
		else
			return 5;
		}
	if(f!=null)
		switch(f.getClass().getSimpleName().charAt(0)){
		case 'E':N=0;break;
		case 'M':N=1;break;
		case 'F':N=2;break;
		case 'N':N=3;break;
		case 'S':N=4;break;
	}
	return N;
}
public ImageIcon ActiveIcon(){
	int n = imgNo();
	ImageIcon img = null;
	Fighter f = game.getPlayer().getActiveFighter();
	img=fighterImgs[n][0];
	if(n==4)
		if(((Saiyan)f).isTransformed())
			img=fighterImgs[4][6];
		else
			img=fighterImgs[4][0];
	return img;
}
public File[] fileList(String dirName,String ext){
	File[] fileList=new File(dirName).listFiles((File pathname) -> {
                if(pathname.isFile() && pathname.getName().endsWith(ext))
                    return true;
                return false;
            });
	return fileList;
}
	public void timer(int timer,Battle b){
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			int i =timer;
			@Override
			public void run() {
				i--;
				if(i==0){
					t.cancel();
						meF.setIcon(ActiveIcon());
						ImageIcon img;
						if(((NonPlayableFighter)foe).isStrong())
							img = fighterImgs[6][0];
						else
							img = fighterImgs[5][0];
						
						foeF.setIcon(img);
				}
			}
		},0,1000 );
	}

public void initialGif(){
	
	meF = new JLabel(ActiveIcon());
	meF.setBounds(0, 0, 1417, 567);
	add(meF);
	ImageIcon img;
	if(((NonPlayableFighter)foe).isStrong())
		img = fighterImgs[6][0];
	else
		img = fighterImgs[5][0];
	foeF = new JLabel(img);
	foeF.setBounds(0, 0, 1417, 567);
	add(foeF);
	//wA
	
	
}

//public void whichAttack(char wA){
//	
//	switch(wA){
//	case 'P' : if(me.getClass().getSimpleName().charAt(0))
//	
//	}
//	
//}
//
//public void whichRace(){
//	char c = me.getClass().getSimpleName().charAt(0);
//	
//	switch(c){
//	case 'S' : 
//	
//	
//	}
//	
//}
		
	}
	
	
	
	
	
	
	
	
