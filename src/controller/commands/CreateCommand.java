package controller.commands;

import model.Document;
import model.DocumentManager;
import model.VersionsManager;
import view.LatexEditorView;

public class CreateCommand implements Command {
	private DocumentManager documentManager;
	private VersionsManager versionsManager;
	private LatexEditorView latexEditorView;
	
	public CreateCommand(DocumentManager documentManager, VersionsManager versionsManager, LatexEditorView latexEditorView) {
		super();
		this.documentManager = documentManager;
		this.versionsManager = versionsManager;
		this.latexEditorView = latexEditorView;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		String type = latexEditorView.getType();
		Document document = documentManager.createDocument(type);
		latexEditorView.setCurrentDocument(document);
	}

}
