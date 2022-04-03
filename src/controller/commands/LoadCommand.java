package controller.commands;

import model.Document;
import model.VersionsManager;
import view.LatexEditorView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadCommand implements Command {
	private LatexEditorView latexEditorView;
	private VersionsManager versionsManager;
	
	public LoadCommand(LatexEditorView latexEditorView, VersionsManager versionsManager) {
		super();
		this.latexEditorView = latexEditorView;
		this.versionsManager = versionsManager;
	}

	public VersionsManager getVersionsManager() {
		return versionsManager;
	}

	public void setVersionsManager(VersionsManager versionsManager) {
		this.versionsManager = versionsManager;
	}

	@Override
	public void execute() {
			// TODO Auto-generated method stub
			String fileContents = "";
			String type;
			String fileName = latexEditorView.getFilename();
			Document currentDocument = latexEditorView.getCurrentDocument();
			try {
				Scanner scanner = new Scanner(new FileInputStream(latexEditorView.getFilename()));
				while(scanner.hasNextLine()) {
					fileContents = fileContents + scanner.nextLine() + "\n";
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (fileName.endsWith(".html")){
			String htmlType = getHtmlDocumentType(fileContents);
			fileContents = convertToLatex(fileContents, htmlType);
			}
			currentDocument.setContents(fileContents);
			type = "emptyTemplate";

			fileContents = fileContents.trim();
			if(fileContents.startsWith("\\documentclass[11pt,twocolumn,a4paper]{article}")) {
				type = "articleTemplate";
			}
			else if(fileContents.startsWith("\\documentclass[11pt,a4paper]{book}")) {
				type = "bookTemplate";
			}
			else if(fileContents.startsWith("\\documentclass[11pt,a4paper]{report}")) {
				type = "ReportTemplate";
			}
			else if(fileContents.startsWith("\\documentclass{letter}")) {
				type = "letterTemplate";
			}
			latexEditorView.setType(type);
	}

	private String getHtmlDocumentType(String fileContents){
		String[] contents = fileContents.split("\n");
		String type = contents[2].replace("<head><title>", "").replace("</title>", "");
		type = type.replace(" ", "");
		if (type.equals("Article")){
			type = "articleTemplate";
		}
		else if (type.equals("Book")){
			type = "bookTemplate";
		}
		else if (type.equals("ReportTemplate")){
			type = "reportTemplate";
		}else{
			type = "letterTemplate";
		}
		return type;
	}

	public String convertToLatex(String contents, String type){
		String latexContents = null;


		if(type.equals("reportTemplate")){
			latexContents = "\n\\documentclass[11pt,a4paper]{report}\n" +
					"\\usepackage{graphicx}\n" +
					"\\begin{document}\n";

		}else if(type.equals("bookTemplate")){
			latexContents = "\n\\documentclass[11pt,a4paper]{book}\n" +
					"\\usepackage{graphicx}\n" +
					"\\begin{document}\n";

		}
		else if(type.equals("articleTemplate")){

			latexContents = "\n\\documentclass[11pt,twocolumn,a4paper]{article}\n" +
					"\\usepackage{graphicx}\n"+
					"\\begin{document}";;

		} else{

			latexContents = "\n\\documentclass{letter}\n" +
					"\\usepackage{hyperref}\n"+
					"\\signature{Sender's Name}\n" +
					"\\address{Sender's address...}\n" +
					"\\begin{document}\n";
		}

		String[] splitContents = contents.split("\n");
		String line;
		for (int i = 0; i < splitContents.length; i++) {

			line = splitContents[i];
			if(line.startsWith("<h1 ")){
				latexContents = latexContents + titleFilter(line) + "\n";
			}
			else if(line.startsWith("<h2 ")){
				latexContents = latexContents + authorFilter(line) + "\n";
			}
			else if(line.startsWith("<p ") && !type.equals("letterTemplate")){
				latexContents = latexContents + activeClock() + "\n";
			}
			else if(line.contains("<h2>")){
				String title = line.split("<h2>")[1].split("</h2>")[0];
				latexContents = latexContents + tagFilter(title) + "\n" ;
			}
			else if(line.contains("<h1>")){
				String title = line.split("<h1>")[1].split("</h1>")[0];
				latexContents = latexContents + tagFilter(title)+ "\n";
			}
			else if(line.contains("<abstract>") && line.contains("</abstract>")){
				String cont = line.split("<abstract>")[1].split("</abstract>")[0];
				latexContents = latexContents + "\n" + cont + "\n";
			}
			else if(line.contains("<p>") && line.contains("</p>")){
				String cont = line.replace("<p>", "").replace("</p>","");
				latexContents = latexContents + "\n" + cont + "\n";

			}
			else if(line.contains("<p>")){
				String cont = line.replace("<p>", "");
				latexContents = latexContents + "\n" + cont + "\n";

			}
			else if(line.contains("<p ") && line.contains("</p>")){
				String cont = line.split("<p ")[1].split("\">")[1].replace("</p>","");

				latexContents = latexContents + "\n" + letterFilter(cont) + "\n";
			}
			else if(line.contains("</p>")){
				String cont = line.replace("</p>", "");
				latexContents = latexContents + "\n" + cont + "\n";

			}
			else if(line.contains("<h1>References</h1>")){
				String title = line.split("<h1>")[1].split("</h1>")[0];
				latexContents = latexContents + tagFilter(title);
			}
			else if(line.startsWith("<h3 ")){
				String[] inputStringArray = line.split("<h3 ")[1].split("\">")[1].split("</h3>");
				if(!inputStringArray[0].contains("Sender's address") && !inputStringArray[0].contains("Sender's Name")) {
					latexContents = latexContents + filterLetter(line) + "\n";
				}
			}
			else if(!line.contains("<") && !line.contains(">")){
				if(line.contains("Yours")){
					latexContents = latexContents + "\\closing{" + line + "}\n";
				}
				else if(line.contains("P.S.")){
					latexContents = latexContents + "\\ps" + "\n" + line + "\n";
				}
				else if(line.contains("abstract")){
					latexContents = latexContents + "\n" + line + "\n" + "\\end{abstract}";
				}
			}

		}
		if(type.equals("letterTemplate")){
			latexContents = latexContents + "\\end{letter}\n" + "\\end{document}" + "\n"  ;
		}
		else {
			latexContents = latexContents + "\\end{document}" + "\n" ;
		}
		return latexContents;
	}

	private String activeClock(){
		return "\\date{\\today}" + "\n" + "\\maketitle" + "\n";
	}

	private String letterFilter(String inside){
		String output = "";
		if(inside.contains("Dear")){
			output = "\\opening{" + inside + "}\n";
		}
		if(inside.contains("Yours")){
			output = "\\closing{" + inside + "}\n";
		}
		else if(inside.contains("P.S.")){
			output = "\\ps" + "\n" + inside + "\n";
		}
		return  output;
	}

	private String titleFilter(String inputString){
		String filteredOutput, title;
		String[] inputStringArray = inputString.split("<h1")[1].split("\">")[1].split("</h1>");
		title = inputStringArray[0];
		filteredOutput = "\\title{" + title + "}" + "\n";
		return filteredOutput;
	}

	private String authorFilter(String inputString){
		String filteredOutput = "\n\\author{";

		String[] inputStringArray = inputString.split("<h2 ")[1].split("\">")[1].split("</h2>");
		String authorName = null;
		String[] input = inputStringArray[0].split("\t\t");


		for (int i =0; i < input.length; i++) {
			authorName = input[i] + "\t\\and " ;
			if (i == input.length - 1){
				authorName =  authorName.replace("\t\\and ", "}");
			}
			filteredOutput = filteredOutput + authorName;
		}

		return filteredOutput;
	}

	private String tagFilter(String type){
		String filteredOutput = "";
		if (type.equals("Abstract")){
			filteredOutput = "\n\\begin{abstract}\n";

		} else if (type.contains(("Chapter")) || type.contains(("chapter"))){
			filteredOutput = "\n\\chapter{"+ type +"}\n";

		} else if (type.equals(("Conclusion"))){
			filteredOutput = "\n\\chapter{"+ type +"}\n";
		}
		else if (type.equals(("References")) && latexEditorView.getType().equals("bookTemplate")){
			filteredOutput = "\n\\chapter*{"+ type +"}\n\\backmatter\n";
		}
		else if (type.equals(("References"))){
			filteredOutput = "\n\\chapter*{"+ type +"}\n";
		}
		else if (type.equals("Preface")){
			filteredOutput = "\n\\fronmatter\n\\chapter{"+ type +"}\n" + "\\mainmatter";
		}
		else if (type.contains(("Section"))){
			filteredOutput = "\n\\section{"+ type +"}\n";
		}
		else if (type.contains(("Last note"))){
			filteredOutput = "\n\\chapter{"+ type +"}\n";
		}

		return filteredOutput;
	}

	private String filterLetter(String inputString){
		String filteredOutput = "";
		String innerContents;

		String[] inputStringArray = inputString.split("<h3 ")[1].split("\">")[1].split("</h3>");
		if(inputStringArray[0].contains("Destination address")){
			filteredOutput = "\\begin{letter}{" +inputStringArray[0] + "}";
		}
		else if(inputStringArray[0].contains("Copyright permission form")){
			filteredOutput = "\\encl{" +inputStringArray[0] + "}";
		}
		return filteredOutput;
	}
}
