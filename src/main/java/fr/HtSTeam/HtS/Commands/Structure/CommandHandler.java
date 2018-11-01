package fr.HtSTeam.HtS.Commands.Structure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * To add a command:
 *<br>create a static public method, name it the name of the command</br>
 *<br>Execution method: it needs to return a boolean and have these parameters : Player sender, org.bukkit.command.Command cmd, String msg, String[] args</br>
 *<br>Completion method: named it the same as the command with no parameters and returns HashMap<String,ArrayList<String>> representing a tree of tab completion</br>
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface CommandHandler {
	Command.Commands value() default Command.Commands.EXECUTE;
}