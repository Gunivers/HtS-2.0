package fr.HtSTeam.HtS.Options.Options.GameMode;

import java.util.ArrayList;

import fr.HtSTeam.HtS.Utils.Files.OptionIO;

public class GameModeIO implements OptionIO {
	
	public GameModeIO() {
		addToList();
	}

	@Override
	public void load(Object o) {
		for(GameModeState gms : GameModeGUI.gameModeOption)
			if(o.toString().equals(gms.getName().substring(2)))
				gms.setState(true);
	}

	@Override
	public ArrayList<String> save() {
		ArrayList<String> element = new ArrayList<String>();
		for(GameModeState gms : GameModeGUI.gameModeOption)
			if(gms.getItemStack().isGlint()) {
				element.add(gms.getName().substring(2));
				return element;
			}
		return null;
	}

	@Override
	public String getId() {
		return "Gamemode";
	}

}
