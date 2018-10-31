package fr.HtSTeam.HtS.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lang {
	
	enum Langs {
		en_UK,
		fr_FR;
	}

	private static Langs lang = Langs.en_UK;
	public static final String REPLACE = "%REPLACE%";
	
	/**
	 * Sets the displayed plugin's language 
	 * @param language
	 */
	public static void setLang(Langs language) {
		lang = language;
	}
	
	/**
	 * Returns the displayed plugin's language
	 * @return lang
	 */
	public static Langs getLang() {
		return lang;
	}
	
	/**
	 * Returns the value of this string in the current language
	 * @param string
	 * @return string
	 */
	public static String get(final String string) {
		try (Stream<String> stream = new BufferedReader(new InputStreamReader(Lang.class.getClassLoader().getResourceAsStream("langs/" + lang.toString() + ".lang"), "UTF8")).lines()) {
			List<String> list = stream.filter(s -> string.matches(s.split("=")[0])).collect(Collectors.toList());
			if (list.isEmpty() || list.get(0).isEmpty())
				return en(string);
			else
				return list.get(0).split("=")[1];
		} catch (Exception e) {
			e.printStackTrace();
			return string;
		}		
	}
	
	/**
	 * Returns the value of this string in english
	 * @param string
	 * @return string
	 */
	private static String en(final String string) {
		try (Stream<String> stream = new BufferedReader(new InputStreamReader(Lang.class.getClassLoader().getResourceAsStream("langs/en_UK.lang"))).lines()) {
			List<String> list = stream.filter(s -> string.matches(s.split("=")[0])).collect(Collectors.toList());
			if (list.isEmpty() || list.get(0).isEmpty())
				return string;
			else
				return list.get(0).split("=")[1];
		} catch (Exception e) {
			e.printStackTrace();
			return string;
		}	
	}
}
