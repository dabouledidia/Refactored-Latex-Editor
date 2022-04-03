import model.Document;
import model.strategies.StableVersionsStrategy;
import model.strategies.VersionsStrategy;
import model.strategies.VolatileVersionsStrategy;
import controller.commands.RollbackToPreviousVersionCommand;
import org.junit.Test;
import javax.swing.JOptionPane;
import controller.LatexEditorController;
import controller.commands.AddLatexCommand;
import model.VersionsManager;
import model.strategies.VolatileVersionsStrategy;
import org.junit.Assert;
import org.junit.Test;
import view.LatexEditorView;
import java.util.ArrayList;
import view.MainWindow;

public class TestUS7 {


    VersionsStrategy versionsStrategy = new VolatileVersionsStrategy();
    LatexEditorView latexEditorView = new LatexEditorView();
    VersionsManager versionsManager = VersionsManager.getInstance(versionsStrategy, latexEditorView);
    Document document = new Document();
    Document doc = new Document();

    @Test
    public void isEnable() {
        doc = versionsStrategy.getVersion();
        versionsManager.enable();
        if (versionsManager.isEnabled() == true){
            versionsManager.putVersion(document);
            versionsManager.rollback();
            document = versionsStrategy.getVersion();
            Assert.assertEquals(doc, document);

       }
        versionsManager.disable();
        Assert.assertEquals(null, latexEditorView.getType());

    }
}