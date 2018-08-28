package fr.HtSTeam.HtS.Events.Structure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import fr.HtSTeam.HtS.Utils.PRIORITY;

@Retention(RUNTIME)
@Target(METHOD)
public @interface EventHandler {
	PRIORITY value() default PRIORITY.NORMAL;
}
