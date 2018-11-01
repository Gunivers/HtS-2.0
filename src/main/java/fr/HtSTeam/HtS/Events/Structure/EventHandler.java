package fr.HtSTeam.HtS.Events.Structure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import fr.HtSTeam.HtS.Utils.PRIORITY;

/**
 * 
 * To add an event:
 * <br>Create a public static method with parameters the Event and Player(HtS)</br>
 * <br>If it doesn't work you need to add it to the event pool int {@link Event Event}, follow the pattern of another one.</br>
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface EventHandler {
	PRIORITY value() default PRIORITY.NORMAL;
}
