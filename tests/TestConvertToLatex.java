import controller.LatexEditorController;
import controller.commands.LoadCommand;
import model.VersionsManager;
import model.strategies.VersionsStrategy;
import model.strategies.VolatileVersionsStrategy;
import org.junit.Test;
import org.junit.Assert;
import view.LatexEditorView;

public class TestConvertToLatex {

    @Test
    public void testLatex(){

        String htmlLetter = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head><h3 style=\"text-align: right;\">Sender's address...</h3>\n" +
                "<p style=\"text-align: right\"> Date: \n" +
                "<script> document.write(new Date().toLocaleDateString()); </script>\n" +
                "</p>\n" +
                "<h3 style=\"text-align: left;\">Destination address....</h3>\n" +
                "<p style=\"text-align: left;\">Dear Sir or Madam:</p>\n" +
                "<p>\n" +
                "\n" +
                "I am writing to you .......\n" +
                "\n" +
                "\n" +
                "</p>\n" +
                "\n" +
                "<p style=\"text-align: center;\">Yours Faithfully,</p>\n" +
                "<p style=\"text-align: center;\">Sender's Name</p>\n" +
                "<p style=\"text-align: left;\"></p>\n" +
                "<p>\n" +
                "\n" +
                "P.S. text .....\n" +
                "\n" +
                "</p>\n" +
                "\n" +
                "<h3 style=\"text-align: left;\">Copyright permission form</h3>\n" +
                "</body>\n" +
                "</html>";

        String expLetter = "\\documentclass{letter}\n" +
                "\\usepackage{hyperref}\n" +
                "\\signature{Sender's Name}\n" +
                "\\address{Sender's address...}\n" +
                "\\begin{document}\n" +
                "\n" +
                "\n" +
                "\\begin{letter}{Destination address....}\n" +
                "\n" +
                "\\opening{Dear Sir or Madam:}\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\\closing{Yours Faithfully,}\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\\ps\n" +
                "P.S. text .....\n" +
                "\n" +
                "\n" +
                "\\encl{Copyright permission form}\n" +
                "\\end{letter}\n" +
                "\\end{document}";

        VersionsStrategy versionsStrategy = new VolatileVersionsStrategy();
        LatexEditorView latexEditorView = new LatexEditorView();
        VersionsManager versionsManager = VersionsManager.getInstance(versionsStrategy, latexEditorView);
        LatexEditorController controller = new LatexEditorController(versionsManager, latexEditorView);
        latexEditorView.setController(controller);
        latexEditorView.setVersionsManager(versionsManager);
        latexEditorView.setType("emptyTemplate");
        LoadCommand testLoad = new LoadCommand(latexEditorView,versionsManager);

        String actualContents = testLoad.convertToLatex(htmlLetter, "letterTemplate");
        Assert.assertEquals(expLetter, actualContents.trim());

    }

}
