import controller.LatexEditorController;
import model.VersionsManager;
import model.strategies.VersionsStrategy;
import model.strategies.VolatileVersionsStrategy;
import org.junit.Assert;
import org.junit.Test;
import view.LatexEditorView;
import view.MainWindow;


public class TestUS1 {

    private String articleTemplate =
            "\\documentclass[11pt,twocolumn,a4paper]{article}\n"+
                    "\\usepackage{graphicx}\n\n" +

                    "\\begin{document}\n\n"+

                    "\\title{Article: How to Structure a LaTeX Document}\n"+
                    "\\author{Author1 \\and Author2 \\and ...}\n"+
                    "\\date{\\today}\n\n"+

                    "\\maketitle\n\n"+

                    "\\section{Section Title 1}\n\n"+

                    "\\section{Section Title 2}\n\n"+

                    "\\section{Section Title.....}\n\n"+

                    "\\section{Conclusion}\n\n"+

                    "\\section*{References}\n\n"+

                    "\\end{document}\n";

    private String bookTemplate =
            "\\documentclass[11pt,a4paper]{book}\n"+
                    "\\usepackage{graphicx}\n\n" +

                    "\\begin{document}\n\n"+

                    "\\title{Book: How to Structure a LaTeX Document}\n"+
                    "\\author{Author1 \\and Author2 \\and ...}\n"+
                    "\\date{\\today}\n\n"+

                    "\\maketitle\n\n"+

                    "\\frontmatter\n\n"+

                    "\\chapter{Preface}\n\n"+

                    "\\mainmatter\n\n"+

                    "\\chapter{First chapter}\n\n"+

                    "\\section{Section Title 1}\n"+
                    "\\section{Section Title 2}\n"+
                    "\\section{Section Title.....}\n\n"+

                    "\\chapter{....}\n\n"+

                    "\\chapter{Conclusion}\n\n"+

                    "\\chapter*{References}\n\n"+

                    "\\backmatter\n\n"+

                    "\\chapter{Last note}\n\n"+

                    "\\end{document}\n";
    private String letterTemplate =
            "\\documentclass{letter}\n"+
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

    private String reportTemplate =
            "\\documentclass[11pt,a4paper]{report}\n\n"+

                    "\\usepackage{graphicx}\n\n" +

                    "\\begin{document}\n\n"+

                    "\\title{Report Template: How to Structure a LaTeX Document}\n"+
                    "\\author{Author1 \\and Author2 \\and ...}\n"+
                    "\\date{\\today}\n"+
                    "\\maketitle\n\n"+

                    "\\begin{abstract}\n"+
                    "Your abstract goes here...\n"+
                    "...\n"+
                    "\\end{abstract}\n\n"+

                    "\\chapter{First Chapter}\n\n"+

                    "\\section{Section Title 1}\n"+
                    "\\section{Section Title 2}\n"+
                    "\\section{Section Title.....}\n\n"+

                    "\\chapter{....}\n\n"+

                    "\\chapter{Conclusion}\n\n\n"+


                    "\\chapter*{References}\n\n"+

                    "\\end{document}\n";
    private String emptyTemplate = "";

    @Test
    public void testTemplates(){
        //Testing DocumentManager
        VersionsStrategy versionsStrategy = new VolatileVersionsStrategy();
        LatexEditorView latexEditorView = new LatexEditorView();
        VersionsManager versionsManager = VersionsManager.getInstance(versionsStrategy, latexEditorView);
        LatexEditorController controller = new LatexEditorController(versionsManager, latexEditorView);
        latexEditorView.setController(controller);
        latexEditorView.setVersionsManager(versionsManager);

        String[] templates = {articleTemplate, bookTemplate, letterTemplate, reportTemplate, emptyTemplate};
        String[] templateNames = {"articleTemplate", "bookTemplate", "letterTemplate",
                "reportTemplate", "emptyTemplate"};
        for (int i = 0; i < templates.length; i++) {
            latexEditorView.setType(templateNames[i]);
            latexEditorView.getController().enact("create");
            MainWindow mainWindow = new MainWindow(latexEditorView);

            Assert.assertEquals(templates[i], mainWindow.getJEditorPane().getText());
        }
    }
}
