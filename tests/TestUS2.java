import controller.LatexEditorController;
import model.Document;
import model.VersionsManager;
import model.strategies.VersionsStrategy;
import model.strategies.VolatileVersionsStrategy;
import org.junit.Assert;
import org.junit.Test;
import view.LatexEditorView;
import view.MainWindow;

public class TestUS2 {

    @Test
    public void testFileEdit(){
        VersionsStrategy versionsStrategy = new VolatileVersionsStrategy();
        LatexEditorView latexEditorView = new LatexEditorView();
        VersionsManager versionsManager = VersionsManager.getInstance(versionsStrategy, latexEditorView);
        LatexEditorController controller = new LatexEditorController(versionsManager, latexEditorView);
        latexEditorView.setController(controller);
        latexEditorView.setVersionsManager(versionsManager);
        latexEditorView.setType("emptyTemplate");
        latexEditorView.getController().enact("create");
        MainWindow mainWindow = new MainWindow(latexEditorView);

        String expectedContents = "This is a test sentence!";
        mainWindow.getJEditorPane().setText(expectedContents);

        String actualContents = mainWindow.getJEditorPane().getText();
        Assert.assertEquals(expectedContents, actualContents);
    }

}
