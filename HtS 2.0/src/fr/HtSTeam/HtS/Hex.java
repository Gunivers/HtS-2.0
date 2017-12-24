package fr.HtSTeam.HtS;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;


public class Hex {

	//@SuppressWarnings("serial")
	//private static ArrayList<Integer> blockId = new ArrayList<Integer>() {{ add(40); add(50); add(60); }};
	
	public static void getPresetId ()
	{
		BigInteger CPN = new BigInteger("1154048504025317376");
		int q = 0;
		ArrayList<Integer> r = new ArrayList<Integer>();
		String CPN_hex = "";
		/*
		for (int i = 0; i < blockId.size(); i++)
		{
			CPN = CPN.add(BigInteger.valueOf(2^(blockId.get(i))));
		}
		*/
		do {
			r.add(CPN.divideAndRemainder(BigInteger.valueOf(16))[1].intValue());
			q = CPN.divideAndRemainder(BigInteger.valueOf(16))[0].intValue();
			CPN = BigInteger.valueOf(q);
		} while (q != 0);
		
		Collections.reverse(r);
		
		for (int i = 0; i < r.size(); i++) {
			CPN_hex = CPN_hex + r.get(i).toString();
		}
		
		System.out.println(CPN_hex);
	}
}
