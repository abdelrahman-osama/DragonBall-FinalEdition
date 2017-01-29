
package dragonball.view;

import javax.swing.*;

import dragonball.controller.BattleView;
import dragonball.controller.DragonMode;
import dragonball.controller.MainMenu;
import dragonball.controller.MapCreateFighter;
import dragonball.controller.NewGame;
import dragonball.controller.WorldMap;
import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.Game;
public class View extends JFrame{

	private MainMenu mainMenu;
	private NewGame newGame;
	private BattleView battleField;
	private DragonMode dragonMode;
	public WorldMap worldMap;
	//load game gui class yet to be implemented
	private static final long serialVersionUID = 1L;
	//private JPanel Panel;
	public View() {
		setTitle("DragonBall Z: The Final Battle");
		
		setSize(1366, 780);
		setLocationRelativeTo(null);
		setResizable(false);
		
		mainMenu = new MainMenu(this);
		add(mainMenu);
		mainMenu.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true); 
	}
	public MainMenu getMainMenu() {
		return mainMenu;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void HideFrame() {
		dispose();
		mainMenu.setVisible(false);
	}
}
