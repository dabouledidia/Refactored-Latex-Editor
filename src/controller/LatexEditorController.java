package controller;

import java.util.HashMap;

import controller.commands.Command;
import controller.commands.CommandFactory;
import model.VersionsManager;
import view.LatexEditorView;

public class LatexEditorController{
	private HashMap<String, Command> commands;
	private String[] commandNames = {"addLatex", "changeVersionsStrategy", "create", "disableVersionsManagement", "edit",
									"enableVersionsManagement", "load", "rollbackToPreviousVersion", "save"};

	public LatexEditorController(VersionsManager versionsManager, LatexEditorView latexEditorView) {
		CommandFactory commandFactory = new CommandFactory(versionsManager, latexEditorView);

		commands = new HashMap<>();
		for (String commandName: commandNames) {
			commands.put(commandName, commandFactory.createCommand((commandName)));
		}
	}
	
	public void enact(String command) {
		commands.get(command).execute();
	}
}
