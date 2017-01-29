package dragonball.controller;
import java.awt.FontFormatException;

import java.io.IOException;

import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.view.View;
public class GUI {
private static final long serialVersionUID = 1L;
private View GameView;

GUI() throws MissingFieldException, UnknownAttackTypeException{
	GameView = new View();
	GameView.setVisible(true);
}
public static void main(String[] args) throws MissingFieldException, UnknownAttackTypeException {
	new GUI();
	}
}