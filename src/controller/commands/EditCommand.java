package controller.commands;

import model.Document;
import model.VersionsManager;
import view.LatexEditorView;

public class EditCommand implements Command {
	private LatexEditorView latexEditorView;
	
	
	public EditCommand(LatexEditorView latexEditorView) {
		super();
		this.latexEditorView = latexEditorView;
	}


	@Override
	public void execute() {
		String text;
		VersionsManager versionsManager = latexEditorView.getVersionsManager();
		Document currentDocument = latexEditorView.getCurrentDocument();

		if(versionsManager.isEnabled()) {
			versionsManager.putVersion(currentDocument);
			currentDocument.changeVersion();
		}
		text = latexEditorView.getText();
		currentDocument.setContents(text);
	}

}
