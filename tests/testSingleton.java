import model.VersionsManager;
import model.strategies.VersionsStrategy;
import model.strategies.VolatileVersionsStrategy;
import org.junit.Assert;
import org.junit.Test;
import view.LatexEditorView;

public class testSingleton {

    @Test
    public void checkSingleton(){
        LatexEditorView latexEditorView1 = new LatexEditorView();
        latexEditorView1.setType("articleTemplate");
        VersionsStrategy versionsStrategy1 = new VolatileVersionsStrategy();
        VersionsManager ver1 = VersionsManager.getInstance(versionsStrategy1, latexEditorView1);

        LatexEditorView latexEditorView2 = new LatexEditorView();
        latexEditorView2.setType("emptyTemplate");
        VersionsStrategy versionsStrategy2 = new VolatileVersionsStrategy();
        VersionsManager ver2 = VersionsManager.getInstance(versionsStrategy2, latexEditorView2);

        Assert.assertEquals(ver1, ver2);
    }
}
