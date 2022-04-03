package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Document {
	private String author;
	private String date;
	private String copyright;
	private String versionID = "0";
	private String contents;
	
	public Document(String author, String date, String copyright, String versionID, String contents) {
		this.author = author;
		this.date = date;
		this.copyright = copyright;
		this.versionID = versionID;
		this.contents = contents;
	}
	
	
	public Document() {
		// TODO Auto-generated constructor stub
		this.contents = "";
	}

	public void save(String filename) {
		try {
			PrintWriter printWriter = new PrintWriter(new FileOutputStream(filename));
			
			printWriter.write(contents);
			printWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Document clone() {
		return new Document(author, date, copyright, versionID, contents);
	}

	public void changeVersion() {
		int n = Integer.parseInt(versionID);
		versionID = (n + 1) + "";
	}

	public String getVersionID() {
		// TODO Auto-generated method stub
		return versionID;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void convertToHtml(){
		String htmlContents = "<!DOCTYPE html>\n" +
				"<html>\n" +
				"<head>";

		String[] splitContents = contents.split("\n");
		String line;
		String tempHeader = "";
		String tempContents = "";
		String tempTitle= "";
		String sendersName = "";
		String tempTableName = "";
		String imageCaption = "";
		String imageSource = "";
		String imageLabel = "";
		ArrayList<String> itemList = new ArrayList<>();
		ArrayList<String[]> tableContents = new ArrayList<>();
		for (int i = 0; i < splitContents.length; i++) {

			line = splitContents[i];
			if((line.startsWith("\\")) && !tempHeader.equals("")){
				htmlContents = htmlContents + filterTag(tempContents, tempHeader, tempTitle);
				tempHeader = "";
				tempContents = "";
				tempTitle= "";
				i--;
			}else if (line.startsWith("\\title")){
				htmlContents = htmlContents + filterTitle(splitContents[i]);
			}else if (line.startsWith("\\author")){
				htmlContents = htmlContents + filterAuthor(splitContents[i]);
			}else if (line.startsWith("\\date")){
				htmlContents = htmlContents + activeClockString();
			}else if (line.startsWith("\\begin{abstract}")) {
				tempHeader = "Abstract";
				tempTitle = "Abstract";
			}else if (line.startsWith("\\chapter")) {
				tempHeader = "Chapter";
				tempTitle = line.split("\\{")[1].replace("}", "");
			}else if (line.startsWith("\\section")){
				tempHeader = "Section";
				tempTitle = line.split("\\{")[1].replace("}", "");
			}else if (line.startsWith("\\subsection")) {
				tempHeader = "Subsection";
				tempTitle = line.split("\\{")[1].replace("}", "");
			}else if (line.startsWith("\\subsubsection")){
				tempHeader = "Subsubsection";
				tempTitle = line.split("\\{")[1].replace("}", "");
			}else if (!line.startsWith("\\") && !tempHeader.equals("")) {
				tempContents = tempContents + line + "\n";
			}else if (line.startsWith("\\signature")){
				sendersName = line.split("\\{")[1].replace("}", "");
			}else if (line.startsWith("\\opening")) {
				tempHeader = "letterOpening";
				tempTitle = line.split("\\{")[1].replace("}", "");
			}else if (line.startsWith("\\ps")){
				tempHeader = "ps";
				tempTitle = "";
			}else if (line.startsWith("\\closing")){
				htmlContents = htmlContents +
						"\n<p style=\"text-align: center;\">Yours Faithfully,</p>\n" +
						"<p style=\"text-align: center;\">" + sendersName + "</p>";
			}else if (line.startsWith("\\item")){
				itemList.add(line.replace("\\item ",""));
			}else if (line.startsWith("\\end{itemize}") || line.startsWith("\\end{enumerate}")) {
				htmlContents = htmlContents + createList(itemList, line);
			}else if (line.startsWith("\\caption")) {
				tempTableName = line.split("\\}")[0].replace("\\caption{", "");
			}else if (!line.startsWith("\\") && !tempTableName.equals("")) {
				tableContents.add(line.split("&"));
			}else if (line.startsWith("\\hline") && !tableContents.isEmpty()){
				htmlContents = htmlContents + createTable(tableContents, tempTableName);
			}else if (line.startsWith("\\includegraphics")){
				String nextLIne = splitContents[i+1];
				imageSource = line.split("\\{")[1].replace("}","");
				imageCaption = nextLIne.split("}")[0].replace("\\caption{", "");
				imageLabel = nextLIne.split("}")[1].replace("\\label{", "");
				htmlContents = htmlContents + createFigure(imageSource, imageCaption, imageLabel);
			}else{
				htmlContents = htmlContents + filterLetterContents(line);
			}
		}
		contents = htmlContents + "\n</body>\n" + "</html>";

	}

	private String activeClockString(){
		return "\n<p style=\"text-align: center;\"> Date: \n" +
				"<script> document.write(new Date().toLocaleDateString()); </script>\n" +
				"</p>";
	}

	private String filterTitle(String inputString){
		String filteredOutput, title, titleAndDescription;
		String[] inputStringArray = inputString.split("\\{")[1].split(":");

		title = inputStringArray[0];
		titleAndDescription = inputStringArray[1].replace("}", "");
		filteredOutput = "<title>" + title + "</title>\n" +
				"</head>\n" +
				"<body>\n" +
				"<h1 style=\"text-align: center;\">" + titleAndDescription + "</h1>";
		return filteredOutput;
	}

	private String filterAuthor(String inputString){
		String filteredOutput = "\n<h2 style=\"text-align: center;\">";

		String[] inputStringArray = inputString.split("\\{")[1].split("\\\\and");
		String authorName = null;
		for (int i =0; i < inputStringArray.length; i++) {
			authorName = inputStringArray[i];
			if (i == inputStringArray.length - 1){
				authorName = authorName.replace("}", "");
			}
			filteredOutput = filteredOutput + "  " + authorName;
		}

		return filteredOutput + "</h2>";
	}

	private String filterTag(String inputString, String type, String title){
		String filteredOutput = "";
		String tag= "";
		if (type.equals("Abstract")){
			filteredOutput = "\n<h2>Abstract</h2>\n";
			tag = "abstract";
		} else if (type.equals(("Chapter"))){
			filteredOutput = "\n<h1>"+title+"</h1>\n";
			tag = "p";
		}else if (type.equals(("Section"))) {
			filteredOutput = "\n<h2>" + title + "</h2>\n";
			tag = "p";
		}else if (type.equals("Subsection")) {
			filteredOutput = "\n<h3>" + title + "</h3>\n";
			tag = "p";
		}else if (type.equals("Subsubsection")){
			filteredOutput = "\n<h4>" + title + "</h4>\n";
			tag = "p";
		}else{
			filteredOutput = "\n<p style=\"text-align: left;\">"+title+"</p>\n";
			tag = "p";
		}

		filteredOutput = filteredOutput + "<"+tag+">\n" + inputString + "</"+tag+">\n";

		return filteredOutput;
	}

	private String filterLetterContents(String inputString){
		String filteredOutput = "";
		String innerContents;

		if (inputString.startsWith("\\address")){
			innerContents = inputString.split("\\{")[1].replace("}", "");
			filteredOutput = "<h3 style=\"text-align: right;\">" + innerContents + "</h3>" +
					"\n<p style=\"text-align: right\"> Date: \n" +
					"<script> document.write(new Date().toLocaleDateString()); </script>\n" +
					"</p>";
		}else if (inputString.startsWith("\\begin{letter}")) {
			innerContents = inputString.split("\\{")[2].replace("}", "");
			filteredOutput = "\n<h3 style=\"text-align: left;\">" + innerContents + "</h3>";
		}else if(inputString.startsWith("\\encl")){
			innerContents = inputString.split("\\{")[1].replace("}", "");
			filteredOutput = "\n<h3 style=\"text-align: left;\">" + innerContents + "</h3>";
		}
		return filteredOutput;
	}

	private String createList(ArrayList<String> itemList, String type){
		String tag;
		if (type.equals("\\end{itemize}")) {
			tag = "ol>";
		}else{
			tag = "ul>";
		}
		String list = "\n<" +tag;
		for (int i = 0; i < itemList.size(); i++ ){
			list = list + "\n<li>" + itemList.get(i) + "</li>";
		}
		itemList.clear();
		return list + "\n</"+tag;
	}

	private String createTable(ArrayList<String[]> tableContents, String tableName){
		String table = "\n<table style=\"width:100%\"> \n<h2>" + tableName + "</h2>";

		for (int i = 0; i < tableContents.size(); i++){
			table = table + "\n<tr>\n";
			for (String column : tableContents.get(i)) {
				table = table + "<th>" + column.replace("\\", "") + "</th>\n";
			}
			table = table + "\n</tr>";
		}
		tableContents.clear();
		return table + "\n</table>";
	}

	private String createFigure(String imageSource, String imageCaption, String imageLabel){
		String outputFigure = "\n<figure>\n" +
				"<img src=" + imageSource + " alt=" + imageLabel + " style=\"width:100%\">\n" +
				"<figcaption>"+imageCaption+"</figcaption>\n" +
				"</figure>\n";
		return outputFigure;
	}
}
