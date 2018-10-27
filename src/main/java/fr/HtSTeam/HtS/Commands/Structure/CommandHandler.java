package fr.HtSTeam.HtS.Commands.Structure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface CommandHandler {
	Command.Commands value() default Command.Commands.EXECUTE;
}