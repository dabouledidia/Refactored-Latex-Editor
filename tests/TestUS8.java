import controller.LatexEditorController;
import model.VersionsManager;
import model.strategies.VersionsStrategy;
import model.strategies.VolatileVersionsStrategy;
import org.junit.Assert;
import org.junit.Test;
import view.LatexEditorView;
import view.MainWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TestUS8 {

    @Test
    public void testOutputFile(){
        VersionsStrategy versionsStrategy = new VolatileVersionsStrategy();
        LatexEditorView latexEditorView = new LatexEditorView();
        VersionsManager versionsManager = VersionsManager.getInstance(versionsStrategy, latexEditorView);
        LatexEditorController controller = new LatexEditorController(versionsManager, latexEditorView);
        latexEditorView.setController(controller);
        latexEditorView.setVersionsManager(versionsManager);
        latexEditorView.setType("articleTemplate");
        latexEditorView.getController().enact("create");
        MainWindow mainWindow = new MainWindow(latexEditorView);

        latexEditorView.setFilename("testFile.tex");
        latexEditorView.getController().enact("save");

        String expectedContents = latexEditorView.getCurrentDocument().getContents();
        String actualContents = readTestFile();
        Assert.assertEquals(expectedContents.trim(), actualContents.trim());

        deleteFile("testFile.tex");
    }

    private String readTestFile(){
        String contents = "";
        try {
            File testFile = new File("testFile.tex");
            Scanner readFile = new Scanner(testFile);
            while (readFile.hasNextLine()) {
                String data = readFile.nextLine();
                contents = contents + "\n" + data;
            }
            readFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return contents + "\n";
    }

    private void deleteFile(String fileName){
        File testFile = new File(fileName);
        if (testFile.delete()) {
            System.out.println("Deleted the file: " + testFile.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
}
