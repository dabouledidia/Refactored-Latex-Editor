package controller.commands;

import model.Document;
import view.LatexEditorView;



public class SaveCommand implements Command {
	private LatexEditorView latexEditorView;
	
	public SaveCommand(LatexEditorView latexEditorView) {
		// TODO Auto-generated constructor stub
		this.latexEditorView = latexEditorView;
	}
	@Override
	public void execute() {
		Document currentDocument = latexEditorView.getCurrentDocument();
		String filename = latexEditorView.getFilename();
		String originalContents = currentDocument.getContents();
		if (filename.endsWith(".html")){
			Document htmlDocument = new Document();
			htmlDocument.setContents(originalContents);
			htmlDocument.convertToHtml();
			htmlDocument.save(filename);
		}else{
			Document texDocument = new Document();
			texDocument.setContents(originalContents);
			texDocument.save(filename);
		}

	}
}
