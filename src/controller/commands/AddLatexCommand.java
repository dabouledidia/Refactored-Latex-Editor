package controller.commands;

import model.Document;
import model.VersionsManager;
import view.LatexEditorView;
import view.MainWindow;
public class AddLatexCommand implements Command  {

	private LatexEditorView latexEditorView;


	public AddLatexCommand(LatexEditorView latexEditorView) {
		super();
		this.latexEditorView = latexEditorView;
	}

	public static void editContents(String type) {


		String contents = MainWindow.getJEditorPane().getText();
		String before = contents.substring(0, MainWindow.getJEditorPane().getCaretPosition());
		String after = contents.substring(MainWindow.getJEditorPane().getCaretPosition());

		String duplicate = before + "\n\\" + type + "{...}"+"\n"+ after;

		String dupli = before +
				"\\begin{" + type + "}\n"+
				"\\item ...\n"+
				"\\item ...\n"+
				"\\end{" + type + "}\n" + after;

		if(type.equals("chapter") || type.equals("subsection") || type.equals("section") ||type.equals("subsubsection")) {
			contents = duplicate;
		}
		else if(type.equals("enumerate") || type.equals("itemize")) {
			contents = dupli;
		}
		else if(type.equals("table")) {
			contents = before +
					"\\begin{table}\n"+
					"\\caption{....}\\label{...}\n"+
					"\\begin{tabular}{|c|c|c|}\n"+
					"\\hline\n"+
					"... &...&...\\\\\n"+
					"... &...&...\\\\\n"+
					"... &...&...\\\\\n"+
					"\\hline\n"+
					"\\end{tabular}\n"+
					"\\end{table}\n"+after;
		}
		else if(type.equals("figure")) {
			contents = before +
					"\\begin{figure}\n"+
					"\\includegraphics[width=...,height=...]{...}\n"+
					"\\caption{....}\\label{...}\n"+
					"\\end{figure}\n"+after;
			;
		}
		MainWindow.getLatexEditorView().setText(contents);
		MainWindow.getLatexEditorView().getController().enact("addLatex");
		MainWindow.getJEditorPane().setText(contents);
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
