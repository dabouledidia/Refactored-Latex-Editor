package model;

import java.util.HashMap;

public class DocumentManager {
	private HashMap<String, Document> templates;

	public DocumentManager() {
		templates = new HashMap<>();
		String[] temp = {"reportTemplate", "bookTemplate", "articleTemplate",
				"letterTemplate", "emptyTemplate"};

		for (String tempNames : temp){
			Document document = new Document();
			if(tempNames != "emptyTemplate"){
				document.setContents(getContents(tempNames));
				templates.put(tempNames, document);
			}
			else{
				templates.put("emptyTemplate", document);
			}
		}
	}

	public Document createDocument(String type) {
		return templates.get(type).clone();
	}

	public String getContents(String type) {
		String sectionsString =
						"\\section{Section Title 1}\n"+
						"\\section{Section Title 2}\n"+
						"\\section{Section Title.....}\n\n" +
								"\\chapter{....}\n\n";


		if(type.equals("articleTemplate")){
			return "\\documentclass[11pt,twocolumn,a4paper]{article}\n"+
					"\\usepackage{graphicx}\n\n" +

					getIntroString("Article")+ "\n" +

					"\\maketitle\n\n"+

					"\\section{Section Title 1}\n\n"+

					"\\section{Section Title 2}\n\n"+

					"\\section{Section Title.....}\n\n"+

					"\\section{Conclusion}\n\n" +

					"\\section*{References}\n\n" +

					"\\end{document}\n";
		}
		else if(type.equals("bookTemplate")) {
			return "\\documentclass[11pt,a4paper]{book}\n"+
					"\\usepackage{graphicx}\n\n" +

					getIntroString("Book") + "\n" +

					"\\maketitle\n\n"+

					"\\frontmatter\n\n"+

					"\\chapter{Preface}\n\n"+

					"\\mainmatter\n\n"+

					"\\chapter{First chapter}\n\n"+

					sectionsString +

					"\\chapter{Conclusion}\n\n"+

					"\\chapter*{References}\n\n"+

					"\\backmatter\n\n"+

					"\\chapter{Last note}\n\n"+

					"\\end{document}\n";
		}
		else if(type.equals("letterTemplate")) {
			return "\\documentclass{letter}\n"+
					"\\usepackage{hyperref}\n"+
					"\\signature{Sender's Name}\n"+
					"\\address{Sender's address...}\n"+
					"\\begin{document}\n\n"+

					"\\begin{letter}{Destination address....}\n"+
					"\\opening{Dear Sir or Madam:}\n\n"+

					"I am writing to you .......\n\n\n"+


					"\\closing{Yours Faithfully,}\n\n"+

					"\\ps\n\n"+

					"P.S. text .....\n\n"+

					"\\encl{Copyright permission form}\n\n"+

					"\\end{letter}\n"+
					"\\end{document}\n";
		}
		else {
			return "\\documentclass[11pt,a4paper]{report}\n\n"+

					"\\usepackage{graphicx}\n\n" +

					getIntroString("Report Template") +
					"\\maketitle\n\n"+

					"\\begin{abstract}\n"+
					"Your abstract goes here...\n"+
					"...\n"+
					"\\end{abstract}\n\n"+

					"\\chapter{First Chapter}\n\n"+

					sectionsString +

					"\\chapter{Conclusion}\n\n\n"+


					"\\chapter*{References}\n\n"+

					"\\end{document}\n";
		}
	}

	private String getIntroString(String templateName){
		String intro =
				"\\begin{document}\n\n"+
						"\\title{" + templateName + ": How to Structure a LaTeX Document}\n"+
						"\\author{Author1 \\and Author2 \\and ...}\n"+
						"\\date{\\today}\n";
		return intro;
	}
}
