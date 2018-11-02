package fr.HtSTeam.HtS.Events.Structure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.PRIORITY;

/**
 * To add an event:
 * <br>Create a public static method with parameters the {@link org.bukkit.event Event} and {@link Player Player(HtS)}.</br>
 * <blockquote><code>public static void onEvent(Event event, Player player)</code></blockquote>
 * If it doesn't work you need to add it to the event pool in {@link Event Event}, follow the pattern of another one.
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface EventHandler {
	PRIORITY value() default PRIORITY.NORMAL;
}
