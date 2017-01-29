package dragonball.model.exceptions;

import dragonball.model.character.fighter.Saiyan;

public class AlreadyTransformedException extends DragonBallException {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Saiyan fighter;

public AlreadyTransformedException(Saiyan fighter){
	super("this" + fighter.getName()+"is already a Super Saiyan");
	this.fighter=fighter;
}
public AlreadyTransformedException(String message, Saiyan fighter){
	super(message);
	this.fighter=fighter;
}
public Saiyan getFighter() {
	return fighter;
}
}
