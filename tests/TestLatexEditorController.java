import controller.commands.Command;
import controller.LatexEditorController;
import model.VersionsManager;
import model.strategies.VersionsStrategy;
import model.strategies.VolatileVersionsStrategy;
import view.LatexEditorView;

import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TestLatexEditorController {

    @Test
    public void testLatexEditorController() {
        VersionsStrategy versionsStrategy = new VolatileVersionsStrategy();
        LatexEditorView latexEditorView = new LatexEditorView();
        VersionsManager versionsManager = VersionsManager.getInstance(versionsStrategy, latexEditorView);
        LatexEditorController testController = new LatexEditorController(versionsManager, latexEditorView);

        try{
            //Access HashMap of commands to test
            Field privateField = LatexEditorController.class.getDeclaredField("commands");
            privateField.setAccessible(true);
            HashMap<String, Command> testCommands = (HashMap<String, Command>)privateField.get(testController);

            ArrayList<String> desiredCommands = new ArrayList<>();
            desiredCommands.add("addLatex");
            desiredCommands.add("changeVersionsStrategy");
            desiredCommands.add("create");
            desiredCommands.add("disableVersionsManagement");
            desiredCommands.add("edit");
            desiredCommands.add("enableVersionsManagement");
            desiredCommands.add("load");
            desiredCommands.add("rollbackToPreviousVersion");
            desiredCommands.add("save");

            ArrayList<String> actualCommands = new ArrayList<>(testCommands.keySet());
            Collections.sort(actualCommands);

            for (int i = 0; i < desiredCommands.size(); i++){
                Assert.assertEquals(desiredCommands.get(i), actualCommands.get(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
