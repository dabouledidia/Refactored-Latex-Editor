import controller.LatexEditorController;
import model.VersionsManager;
import model.strategies.VersionsStrategy;
import model.strategies.VolatileVersionsStrategy;
import org.junit.Assert;
import org.junit.Test;
import view.LatexEditorView;
import view.MainWindow;

import java.io.File; 
import java.io.IOException;
import java.io.FileWriter;


public class TestUS9 {

    @Test
    public void testInputFile(){
        VersionsStrategy versionsStrategy = new VolatileVersionsStrategy();
        LatexEditorView latexEditorView = new LatexEditorView();
        VersionsManager versionsManager = VersionsManager.getInstance(versionsStrategy, latexEditorView);
        LatexEditorController controller = new LatexEditorController(versionsManager, latexEditorView);
        latexEditorView.setController(controller);
        latexEditorView.setVersionsManager(versionsManager);
        latexEditorView.setType("emptyTemplate");
        latexEditorView.getController().enact("create");
        MainWindow mainWindow = new MainWindow(latexEditorView);

        String expectedContents = "This is test sentence!";
        createTestFile(expectedContents);

        latexEditorView.setFilename("testFile.tex");
        latexEditorView.getController().enact("load");
        mainWindow.getJEditorPane().setText(latexEditorView.getCurrentDocument().getContents());
        Assert.assertEquals(expectedContents, mainWindow.getJEditorPane().getText().trim());

        deleteFile("testFile.tex");
    }

    private void createTestFile(String contents){
        boolean createdFile = false;
        boolean wroteToFile = false;

        try {
            File openTestFile = new File("testFile.tex");
            if (openTestFile.createNewFile()) {
                createdFile = true;
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            createdFile = false;
            e.printStackTrace();
        }

        try {
            FileWriter writeToTestFile = new FileWriter("testFile.tex");
            writeToTestFile.write(contents);
            writeToTestFile.close();
            wroteToFile = true;
        } catch (IOException e) {
            wroteToFile = false;
            e.printStackTrace();
        }
        if (createdFile && wroteToFile){
            System.out.println("Created test file \"testFile.tex\"");
        }
    }

    private void deleteFile(String fileName){
        File testFile = new File(fileName);
        if (testFile.canWrite()) {
            if (testFile.delete()) {
                System.out.println("Deleted the file: " + testFile.getName());
            } else {
                System.out.println("Failed to delete the file.");
            }
        }
    }

}
