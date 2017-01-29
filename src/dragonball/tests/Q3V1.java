package dragonball.tests;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import dragonball.model.attack.Attack;
import dragonball.model.attack.PhysicalAttack;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.SuperSaiyan;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleOpponent;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.exceptions.AlreadyTransformedException;
import dragonball.model.exceptions.DragonBallException;
import dragonball.model.exceptions.NotASaiyanException;
import dragonball.model.exceptions.NotEnoughKiException;
import dragonball.model.game.Game;

public class Q3V1 {
	private int thrown = 1;
	private int attacked = 0;

	@Test(timeout = 1000)
	public void AlreadyTransformedExceptionInheritance() throws Exception {
		assertEquals(
				"AlreadyTransformedException class should extend DragonBallException",
				DragonBallException.class,
				AlreadyTransformedException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testAlreadyTransformedExceptionClassVariables()
			throws Exception {
		Field f = null;
		boolean thrown = false;
		try {
			f = AlreadyTransformedException.class.getDeclaredField("fighter");
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertFalse(
				"there should be \"fighter\" instance variable in class AlreadyTransformedException",
				thrown);
		assertEquals(
				"\"fighter\" instance variable in class AlreadyTransformedException should be of type Saiyan",
				f.getType(), Saiyan.class);

	}

	@Test(timeout = 1000)
	public void testAlreadyTransformedExceptionClassVariablesAccessibility()
			throws Exception {

		Field f = AlreadyTransformedException.class.getDeclaredField("fighter");
		assertEquals(
				"\"fighter\" instance variable in class AlreadyTransformedException should not be accessed outside that class",
				2, f.getModifiers());

	}

	@Test(timeout = 1000)
	public void testAlreadyTransformedExceptionClassREADVariables()
			throws Exception {
		Method[] methods = AlreadyTransformedException.class
				.getDeclaredMethods();

		assertTrue(
				"The \"fighter\" instance variable in class AlreadyTransformedException is a READ variable.",
				containsMethodName(methods, "getFighter"));

		try {
			Method m = AlreadyTransformedException.class
					.getDeclaredMethod("getFighter");
			assertTrue(
					"incorrect return type for \"getFighter\" method in AlreadyTransformedException class.",
					m.getReturnType().equals(Saiyan.class));
		} catch (Exception e) {
			fail("Missing \"getFighter\" method in AlreadyTransformedException class which takes no input parameters.");
		}

		
		assertFalse(
				"The \"fighter\" instance variable in class AlreadyTransformedException is a READ ONLY variable.",
				containsMethodName(methods, "setFighter"));

	}

	@Test(timeout = 1000)
	public void testAlreadyTransformedExceptionFirstConstructor()
			throws Exception {
		Class<AlreadyTransformedException> aClass = AlreadyTransformedException.class;
		boolean thrown = false;
		try {
			aClass.getConstructor(new Class[] { Saiyan.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with 1 parameter in AlreadyTransformedException class.",
				thrown);
		Saiyan s = new Saiyan("TestSaiyan");
		AlreadyTransformedException e = new AlreadyTransformedException(s);
		Field var = AlreadyTransformedException.class.getDeclaredField("fighter");
		var.setAccessible(true);
		assertTrue("Constructor with 1 parameter should set the message of the exception and the message should include the fighter's name.",e.getMessage().contains("TestSaiyan"));
		assertEquals("Constructor with 1 parameter should set the fighter variable", s,var.get(e));
	}

	@Test(timeout = 1000)
	public void testAlreadyTransformedExceptionSecondConstructor()
			throws Exception {
		Class<AlreadyTransformedException> aClass = AlreadyTransformedException.class;
		boolean thrown = false;
		try {
			aClass.getConstructor(new Class[] { String.class, Saiyan.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with 2 parameters in AlreadyTransformedException class.",
				thrown);
		String message = "test"+Math.random()*10000+"TestSaiyan";
		Saiyan s = new Saiyan("TestSaiyan");
		
		AlreadyTransformedException e = new AlreadyTransformedException(message,s);
		Field var = AlreadyTransformedException.class.getDeclaredField("fighter");
		var.setAccessible(true);
		assertTrue("Constructor with 2 parameters should set the message of the exception",e.getMessage().contains(message));
		assertTrue("The message of the exception should include the fighter's name.",e.getMessage().contains("TestSaiyan"));
		assertEquals("Constructor with 2 parameters should set the fighter variable", s,var.get(e));
	}

	@Test(timeout = 1000)
	public void testThrow_AlreadyTransformedException_SuperSaiyan()
			throws Exception {

		Saiyan me = new Saiyan("saiyan");
		ArrayList<UltimateAttack> ultimateAttacks = new ArrayList<UltimateAttack>();
		ultimateAttacks.add(new SuperSaiyan());
		me.setUltimateAttacks(ultimateAttacks);

		NonPlayableFighter foe = new NonPlayableFighter("fighter", 1, 1, 10,
				1250, 50, 40, false, null, null);
		Battle b = new Battle(me, foe);

		if (b.getAttacker() != me)
			b.switchTurn();
		boolean thrown = false;
		me.setKi(10);
		me.setTransformed(true);
		Attack attack = ultimateAttacks.get(0);
		try {
			// b.attack(me.getUltimateAttacks().get(0));
			attack.onUse(me, foe, false);

		} catch (AlreadyTransformedException e) {
			thrown = true;
		}
		assertTrue(
				"AlreadyTransformedException should not be thrown if the user tries to use a Super Saiyan attack while he/she is already transformed",
				thrown);

	}

	@Test(timeout = 1000)
	public void testThrownVariablesAlreadyTransformedException_SuperSaiyan()
			throws Exception {
		Saiyan me = new Saiyan("saiyan");
		ArrayList<UltimateAttack> ultimateAttacks = new ArrayList<UltimateAttack>();
		ultimateAttacks.add(new SuperSaiyan());
		me.setUltimateAttacks(ultimateAttacks);

		NonPlayableFighter foe = new NonPlayableFighter("fighter", 1, 1, 10,
				1250, 50, 40, false, null, null);
		Battle b = new Battle(me, foe);

		if (b.getAttacker() != me)
			b.switchTurn();
		boolean thrown = false;
		boolean fighter = false;
		boolean message = false;
		me.setKi(10);
		me.setTransformed(true);
		Attack attack = ultimateAttacks.get(0);
		try {
			// b.attack(me.getUltimateAttacks().get(0));
			attack.onUse(me, foe, false);
		} catch (AlreadyTransformedException e) {
			thrown = true;
			Field var = AlreadyTransformedException.class.getDeclaredField("fighter");
			var.setAccessible(true);
			
			if (((Fighter)var.get(e)) == me)
				fighter = true;
			if (e.getMessage().contains(me.getName()))
				message = true;
		}
		assertTrue(
				"AlreadyTransformedException should not be thrown if the user tries to use a Super Saiyan attack while he/she is already transformed",
				thrown);
		assertTrue(
				"The fighter which is trying to transform should be set in AlreadyTransformedException.",
				fighter);
		assertTrue(
				"The message of AlreadyTransformedException should include name of the fighter which is trying to transform.",
				message);
	}

	@Test(timeout = 1000)
	public void testAttack_Handles_AlreadyTransformedException()
			throws Exception {

		for (int trials = 0; trials < 100; trials++) {

			thrown = 0;
			attacked = 0;

			Game g = new Game();
			Battle battle = null;
			final String testMessage = "Test" + (Math.random() * 10000)
					+ "Message";

			final Saiyan me = new Saiyan("saiyan");
			me.setTransformed(true);

			me.setSuperAttacks(new ArrayList<SuperAttack>());

			ArrayList<UltimateAttack> ultimateAttacks = new ArrayList<UltimateAttack>();
			ultimateAttacks.add(new UltimateAttack("Super Kamehameha", 450) {
				public void onUse(BattleOpponent attacker,
						BattleOpponent defender, boolean defenderBlocking)
						throws RuntimeException, AlreadyTransformedException {
					attacked++;
					if (attacked == 1)
						throw new AlreadyTransformedException(testMessage, me);
					else
						try {
							super.onUse(attacker, defender, defenderBlocking);
						} catch (Exception e) {
							e.printStackTrace();
							fail("An exception occured while attacking."
									+ e.getMessage());
							
						}
				}
			});
			ultimateAttacks.add(new SuperSaiyan() {
				public void onUse(BattleOpponent attacker,
						BattleOpponent defender, boolean defenderBlocking)
						throws RuntimeException, AlreadyTransformedException {
					attacked++;
					if (attacked == 1)
						throw new AlreadyTransformedException(testMessage, me);
					else
						try {
							super.onUse(attacker, defender, defenderBlocking);
						}catch (Exception e) {
								e.printStackTrace();
								fail("An exception occured while attacking."
										+ e.getMessage());
								
							}
				}
			});
			me.setUltimateAttacks(ultimateAttacks);

			ArrayList<PlayableFighter> fighters = new ArrayList<PlayableFighter>();
			fighters.add(me);
			g.getPlayer().setFighters(fighters);
			g.getPlayer().setActiveFighter(me);

			NonPlayableFighter foe = new NonPlayableFighter("fighter", 1, 1000,
					10, 1250, 50, 40, false, null, null);

			battle = new Battle(me, foe) {
				public void attack(Attack attack) throws RuntimeException {
					if (attack instanceof PhysicalAttack)
						attacked++;
					try {
						super.attack(attack);
					} catch (Exception e) {
						e.printStackTrace();
						fail("An exception occured while attacking."
								+ e.getMessage());
						
					}
				}
			};

			if (battle.getAttacker() != me) {
				battle.endTurn();
				if (battle.getAttacker() != me)
					fail("End turn should end the turn and switch the players.");
			}

			me.setKi(10);
			int hpBefore = foe.getHealthPoints();

			try {

				battle.attack(ultimateAttacks.get(0));

			} catch (Exception e) {
				if (e instanceof AlreadyTransformedException)
					fail("Method Attack in Battle should handle AlreadyTransformedException");
				throw e;
			}

			int hpAfter = foe.getHealthPoints();
			if (hpAfter == hpBefore - 50 - me.getPhysicalDamage())
				attacked++;

			assertTrue(
					"Attack method should utilize the attack's onUse method.",
					attacked > 0);

			assertTrue(
					"If the fighter failed to attack because it's already transformed then you should attack with a Random Attack instead.",
					attacked > 1);

		}
	}

	@Test(timeout = 1000)
	public void testAttack_Catch_AlreadyTransformedException() throws Exception {

		for (int trials = 0; trials < 100; trials++) {

			thrown = 0;

			Game g = new Game();
			Battle battle = null;
			final String testMessage = "Test" + (Math.random() * 10000)
					+ "Message";

			final Saiyan me = new Saiyan("saiyan");
			me.setTransformed(true);

			ArrayList<UltimateAttack> ultimateAttacks = new ArrayList<UltimateAttack>();
			ultimateAttacks.add(new SuperSaiyan() {
				public void onUse(BattleOpponent attacker,
						BattleOpponent defender, boolean defenderBlocking)
						throws RuntimeException, AlreadyTransformedException {
					attacked++;
					if (attacked == 1)
						throw new AlreadyTransformedException(testMessage, me);
					try {
						super.onUse(attacker, defender, defenderBlocking);
					} catch (Exception e) {
						e.printStackTrace();
						fail("An exception occured while attacking."
								+ e.getMessage());
						
					}
				}
			});
			me.setUltimateAttacks(ultimateAttacks);

			ArrayList<PlayableFighter> fighters = new ArrayList<PlayableFighter>();
			fighters.add(me);
			g.getPlayer().setFighters(fighters);
			g.getPlayer().setActiveFighter(me);

			NonPlayableFighter foe = new NonPlayableFighter("fighter", 1, 1,
					10, 1250, 50, 40, false, null, null);

			battle = new Battle(me, foe);

			me.setKi(10);

			try {

				battle.attack(ultimateAttacks.get(0));

			} catch (Exception e) {
				if (e instanceof AlreadyTransformedException)
					fail("Method Attack in Battle should handle AlreadyTransformedException");
				throw e;
			}
			assertTrue(
					"Attack method should utilize the attack's onUse method.",
					attacked > 0);

		}
	}

	public static boolean containsMethodName(Method[] methods, String name) {
		for (Method method : methods) {
			if (method.getName().equals(name))
				return true;
		}
		return false;
	}
}
