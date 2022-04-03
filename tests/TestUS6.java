
import model.strategies.VersionsStrategy;
import model.strategies.VolatileVersionsStrategy;
import org.junit.Test;
import model.VersionsManager;
import org.junit.Assert;
import view.LatexEditorView;



public class TestUS6 {

    VersionsStrategy versionsStrategy = new VolatileVersionsStrategy();
    LatexEditorView latexEditorView = new LatexEditorView();
    VersionsManager versionsManager = VersionsManager.getInstance(versionsStrategy, latexEditorView);
    boolean expEnable = true;
    boolean expDisable = false;


    @Test
    public void enableMechanism(){
        versionsManager.enable();
        boolean enable = versionsManager.isEnabled();
        Assert.assertEquals(expEnable, enable);
    }

    @Test
    public void disableMechanism(){
        versionsManager.disable();
        boolean disable = versionsManager.isEnabled();
        Assert.assertEquals(expDisable, disable);
    }

}
