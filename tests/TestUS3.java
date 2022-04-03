import controller.LatexEditorController;
import controller.commands.AddLatexCommand;
import model.VersionsManager;
import model.strategies.VersionsStrategy;
import model.strategies.VolatileVersionsStrategy;
import org.junit.Assert;
import org.junit.Test;
import view.LatexEditorView;
import view.MainWindow;

import javax.swing.JMenuBar;


public class TestUS3 {

    @Test
    public void testAddLatexCommand(){
        String[] templateNames = {"articleTemplate", "bookTemplate", "letterTemplate",
                "reportTemplate", "emptyTemplate"};

        for (int i = 0; i < templateNames.length; i++){
            if (templateNames[i].equals("articleTemplate")){
                Assert.assertFalse(testAddChapterCommand(templateNames[i]));
            }
            if ( templateNames[i].equals("letterTemplate")){
                Assert.assertFalse(testCommandsTab(templateNames[i]));
            }
            Assert.assertTrue(testCommandTemplate(templateNames[i]));
        }

    }

    private boolean testCommandTemplate(String templateName){
        VersionsStrategy versionsStrategy = new VolatileVersionsStrategy();
        LatexEditorView latexEditorView = new LatexEditorView();
        VersionsManager versionsManager = VersionsManager.getInstance(versionsStrategy, latexEditorView);
        LatexEditorController controller = new LatexEditorController(versionsManager, latexEditorView);
        latexEditorView.setController(controller);
        latexEditorView.setVersionsManager(versionsManager);
        MainWindow mainWindow;

        String[] types = {"chapter", "subsection", "section", "subsubsection", "enumerate", "itemize",
                "table", "figure"};
        String expectedString;
        String actualContents;
        String before;
        String after;

        for (int i = 0; i < types.length; i++) {

            latexEditorView.setType(templateName);
            latexEditorView.getController().enact("create");
            mainWindow = new MainWindow(latexEditorView);
            actualContents = MainWindow.getJEditorPane().getText();
            before =  actualContents.substring(0, MainWindow.getJEditorPane().getCaretPosition());
            after =  actualContents.substring(MainWindow.getJEditorPane().getCaretPosition());

            if(i < 4){
                expectedString = before + "\n\\" + types[i] + "{...}" + "\n" + after;
                //System.out.println(mainWindow.frame.getJMenuBar().getMenu(0).getMenuComponent(0).);
            }else if(i == 4 || i == 5){
                expectedString = before + "\\begin{" + types[i] + "}\n"+
                        "\\item ...\n"+
                        "\\item ...\n"+
                        "\\end{" + types[i] + "}\n" + after;
            }else if (i == 6){
                expectedString = before + "\\begin{table}\n"+
                        "\\caption{....}\\label{...}\n"+
                        "\\begin{tabular}{|c|c|c|}\n"+
                        "\\hline\n"+
                        "... &...&...\\\\\n"+
                        "... &...&...\\\\\n"+
                        "... &...&...\\\\\n"+
                        "\\hline\n"+
                        "\\end{tabular}\n"+
                        "\\end{table}\n" + after;

            }else {
                expectedString = before +  "\\begin{figure}\n"+
                        "\\includegraphics[width=...,height=...]{...}\n"+
                        "\\caption{....}\\label{...}\n"+
                        "\\end{figure}\n" + after;
            }
            AddLatexCommand.editContents(types[i]);
            if (!expectedString.equals(MainWindow.getJEditorPane().getText()))
                return false;
        }
        return true;
    }

    private boolean testAddChapterCommand(String templateName){
        VersionsStrategy versionsStrategy = new VolatileVersionsStrategy();
        LatexEditorView latexEditorView = new LatexEditorView();
        VersionsManager versionsManager = VersionsManager.getInstance(versionsStrategy, latexEditorView);
        LatexEditorController controller = new LatexEditorController(versionsManager, latexEditorView);
        latexEditorView.setController(controller);
        latexEditorView.setVersionsManager(versionsManager);
        latexEditorView.setType(templateName);
        latexEditorView.getController().enact("create");
        MainWindow mainWindow = new MainWindow(latexEditorView);

        JMenuBar menuBar = (JMenuBar) mainWindow.frame.getContentPane().getComponent(0);
        boolean addChapterStatus = menuBar.getMenu(1).getMenuComponent(0).isEnabled();

        return addChapterStatus;
    }

    private boolean testCommandsTab(String templateName){
        VersionsStrategy versionsStrategy = new VolatileVersionsStrategy();
        LatexEditorView latexEditorView = new LatexEditorView();
        VersionsManager versionsManager = VersionsManager.getInstance(versionsStrategy, latexEditorView);
        LatexEditorController controller = new LatexEditorController(versionsManager, latexEditorView);
        latexEditorView.setController(controller);
        latexEditorView.setVersionsManager(versionsManager);
        latexEditorView.setType(templateName);
        latexEditorView.getController().enact("create");
        MainWindow mainWindow = new MainWindow(latexEditorView);

        JMenuBar menuBar = (JMenuBar) mainWindow.frame.getContentPane().getComponent(0);
        boolean status = menuBar.getComponent(1).isEnabled();
        return status;
    }

}
