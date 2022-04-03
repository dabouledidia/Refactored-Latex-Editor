import controller.LatexEditorController;
import model.Document;
import model.VersionsManager;
import model.strategies.StableVersionsStrategy;
import model.strategies.VersionsStrategy;
import model.strategies.VolatileVersionsStrategy;
import org.junit.After;
import view.LatexEditorView;
import view.MainWindow;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.PublicKey;
import java.util.Scanner;

public class TestUS5 {


    @Test
    public void testStableStrategy(){
        LatexEditorView latexEditorView = new LatexEditorView();
        VersionsStrategy versionsStrategy = new StableVersionsStrategy();

        String expectedContents = "This is a test sentence! (STABLE STRATEGY)";
        Document testDocument = new Document();
        latexEditorView.setCurrentDocument(testDocument);

        testDocument.setContents(expectedContents);
        versionsStrategy.putVersion(testDocument);

        String actualContents = readVersion(testDocument.getVersionID());
        Assert.assertEquals(expectedContents, actualContents.trim());
    }

    @Test
    public void testVolatileStrategy(){
        LatexEditorView latexEditorView = new LatexEditorView();
        VersionsStrategy versionsStrategy = new VolatileVersionsStrategy();

        String expectedContents = "This is a test sentence! (VOLATILE STRATEGY)";
        Document testDocument = new Document();
        latexEditorView.setCurrentDocument(testDocument);

        testDocument.setContents(expectedContents);
        versionsStrategy.putVersion(testDocument);

        int documentVersionID = Integer.parseInt(testDocument.getVersionID());
        String actualContents = versionsStrategy.getEntireHistory().get(documentVersionID).getContents();
        Assert.assertEquals(expectedContents, actualContents.trim());
    }

    private String readVersion(String versionID){
        String contents = "";
        try {
            File testFile = new File(versionID+".tex");
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
        return contents;
    }

}
