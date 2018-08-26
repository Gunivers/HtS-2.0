package fr.HtSTeam.HtS.Options.Structure.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import fr.HtSTeam.HtS.Utils.PRIORITY;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Timer {
	PRIORITY value() default PRIORITY.NORMAL; 
}