package fr.HtSTeam.HtS.Utils;

import java.lang.reflect.Field;

public class ReflectionUtil {
	
	/**
	 * Returns the index of the specified field name
	 * 
	 * @param fields
	 * @param field_name
	 * @return index
	 */
	public static int fieldIndex(Field[] fields, String field_name) {
		for (int i = 0; i< fields.length; i++)
			if (fields[i].getName().equals(field_name))
				return i;
		return 0;
	}
}
