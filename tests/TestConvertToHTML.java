import model.Document;
import org.junit.Assert;
import org.junit.Test;

public class TestConvertToHTML {

    @Test
    public void testHTML(){
        String testTemplate = "\n" +
                "\\chapter{...}\n" +
                "\n" +
                "\n" +
                "\\section{...}\n" +
                "\n" +
                "\\subsection{...}\n" +
                "\n" +
                "\\subsubsection{...}\n" +
                "\n" +
                "\\begin{itemize}\n" +
                "\\item ...\n" +
                "\\item ...\n" +
                "\\end{itemize}\n" +
                "\n" +
                "\\begin{enumerate}\n" +
                "\\item ...\n" +
                "\\item ...\n" +
                "\\end{enumerate}\n" +
                "\n" +
                "\\begin{table}\n" +
                "\\caption{....}\\label{...}\n" +
                "\\begin{tabular}{|c|c|c|}\n" +
                "\\hline\n" +
                "... &...&...\\\\\n" +
                "... &...&...\\\\\n" +
                "... &...&...\\\\\n" +
                "\\hline\n" +
                "\\end{tabular}\n" +
                "\\end{table}\n" +
                "\n" +
                "\\begin{figure}\n" +
                "\\includegraphics[width=...,height=...]{...}\n" +
                "\\caption{....}\\label{...}\n" +
                "\\end{figure}";
        String expectedOutput = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<h1>...</h1>\n" +
                "<p>\n" +
                "\n" +
                "\n" +
                "</p>\n" +
                "\n" +
                "<h2>...</h2>\n" +
                "<p>\n" +
                "\n" +
                "</p>\n" +
                "\n" +
                "<h3>...</h3>\n" +
                "<p>\n" +
                "\n" +
                "</p>\n" +
                "\n" +
                "<h4>...</h4>\n" +
                "<p>\n" +
                "\n" +
                "</p>\n" +
                "\n" +
                "<ol>\n" +
                "<li>...</li>\n" +
                "<li>...</li>\n" +
                "</ol>\n" +
                "<ul>\n" +
                "<li>...</li>\n" +
                "<li>...</li>\n" +
                "</ul>\n" +
                "<table style=\"width:100%\"> \n" +
                "<h2>....</h2>\n" +
                "<tr>\n" +
                "<th>... </th>\n" +
                "<th>...</th>\n" +
                "<th>...</th>\n" +
                "\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<th>... </th>\n" +
                "<th>...</th>\n" +
                "<th>...</th>\n" +
                "\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<th>... </th>\n" +
                "<th>...</th>\n" +
                "<th>...</th>\n" +
                "\n" +
                "</tr>\n" +
                "</table>\n" +
                "<figure>\n" +
                "<img src=... alt=... style=\"width:100%\">\n" +
                "<figcaption>....</figcaption>\n" +
                "</figure>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

        Document texDocument = new Document();
        texDocument.setContents(testTemplate);

        texDocument.convertToHtml();
        Assert.assertEquals(expectedOutput, texDocument.getContents());
    }

}
