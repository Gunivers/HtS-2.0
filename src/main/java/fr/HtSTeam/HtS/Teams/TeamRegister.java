package fr.HtSTeam.HtS.Teams;

import java.util.ArrayList;

import fr.HtSTeam.HtS.Utils.Tag;
import fr.HtSTeam.HtS.Utils.Files.OptionIO;

public class TeamRegister implements OptionIO {
	
	public TeamRegister() {
		addToList();
	}
	
	@Override
	public void load(Object o) {
		if (o instanceof String) {
			new Team(((String) o).split("#/#")[0], ((String) o).split("#/#")[1]);
		} else {
			Tag option_tag = (Tag) o;
			for (Tag t : option_tag.values)
				new Team(t.values.get(0).name.split("#/#")[0], t.values.get(0).name.split("#/#")[1]);
		}
	}

	@Override
	public ArrayList<String> save() {
		if (Team.teamList.isEmpty())
			return null;
		ArrayList<String> teams = new ArrayList<String>();
		for(Team team : Team.teamList)
			teams.add(team.getTeamName() + "#/#" + team.getTeamColor());
		return teams;
	}

	@Override
	public String getId() {
		return "teams";
	}
}
