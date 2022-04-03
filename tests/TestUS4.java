import model.Document;
import model.strategies.StableVersionsStrategy;
import model.strategies.VersionsStrategy;
import model.strategies.VolatileVersionsStrategy;
import org.junit.Test;
import controller.LatexEditorController;
import controller.commands.AddLatexCommand;
import model.VersionsManager;
import model.strategies.VolatileVersionsStrategy;
import org.junit.Assert;
import org.junit.Test;
import view.LatexEditorView;
import java.util.ArrayList;
import view.MainWindow;

public class TestUS4 {

    VersionsStrategy versionsStrategy = new VolatileVersionsStrategy();
    VolatileVersionsStrategy volatileVersionsStrategy = new VolatileVersionsStrategy();
    StableVersionsStrategy stableVersionsStrategy = new StableVersionsStrategy();
    LatexEditorView latexEditorView = new LatexEditorView();
    VersionsManager versionsManager = VersionsManager.getInstance(versionsStrategy, latexEditorView);

    Document document = new Document();
    String filename;
    ArrayList<Document> history = new ArrayList<Document>();

    @Test
    public void testStrategies(){
        ArrayList<String> storingStrategies = new ArrayList<String>();
        ArrayList<String> expectingStoringStrategies = new ArrayList<String>();
        expectingStoringStrategies.add("Volatile");
        expectingStoringStrategies.add("Stable");

        for(String i : expectingStoringStrategies){
            latexEditorView.setStrategy(i);
            storingStrategies.add(latexEditorView.getStrategy());
        }
        Assert.assertEquals(expectingStoringStrategies, storingStrategies);


    }

}
