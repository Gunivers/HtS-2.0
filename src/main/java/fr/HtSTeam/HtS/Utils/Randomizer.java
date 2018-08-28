package fr.HtSTeam.HtS.Utils;

import java.util.Random;

public class Randomizer {

	private static Random random = new Random();
	
	public Randomizer() { }
	
	/**
	 * @param n borne maximum du randomizer
	 * @return un nombre aléatoire entre (inclu) et n (exclu)
	 */
	public static int rand(int n) {
		random = new Random();
		return random.nextInt(n);
	}

	/**
	 * @param min borne minimum du randomizer
	 * @param max borne maximum du randomizer
	 * @return un nombre aléatoire entre min (inclu) et max (inclu)
	 */
	public static int randI(int min, int max) {
		random = new Random();
		return random.nextInt(max + 1 - min) + min;
	}

	/**
	 * @param rate un nombre en 0 et 100
	 * @return une booléenne aléatoire suivant le pourcentage rentré
	 */
	public static boolean randRate(int rate) {
		if (rand(100) < rate)
			return true;
		return false;
	}

	/**
	 * @param mx un X minimum
	 * @param Mx un X maximum
	 * @param my un Y minimum
	 * @param My un Y maximum
	 * @param mz un Z minimum
	 * @param Mz un Z maximum
	 * @return Une location (X, Y, Z) aléatoire en fonction des paramètres entrés
	 */
	public static int[] randCoord(int mx, int Mx, int my, int My, int mz, int Mz) {
		int coords[] = new int[3];
		coords[0] = randI(mx, Mx);
		coords[1] = randI(my, My);
		coords[2] = randI(mz, Mz);
		return coords;
	}
}