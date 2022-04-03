import model.DocumentManager;
import model.Document;

import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class TestDocumentManager {
    @Test
    public void testDocumentManager() {
        DocumentManager testDocuments = new DocumentManager();
        try{
            //Access HashMap of templates to test
            Field privateField = DocumentManager.class.getDeclaredField("templates");
            privateField.setAccessible(true);
            HashMap<String, Document> testTemplates = (HashMap<String, Document>)privateField.get(testDocuments);
            ArrayList<String> desiredTemplates = new ArrayList<>();
            desiredTemplates.add("reportTemplate");
            desiredTemplates.add("bookTemplate");
            desiredTemplates.add("letterTemplate");
            desiredTemplates.add("articleTemplate");
            desiredTemplates.add("emptyTemplate");
            ArrayList<String> actualTemplates = new ArrayList<>(testTemplates.keySet());
            Collections.sort(actualTemplates);
            Collections.sort(desiredTemplates);
            //System.out.println(desiredTemplates + "\n " + actualTemplates);
            for (int i = 0; i < desiredTemplates.size(); i++){
                Assert.assertEquals(desiredTemplates.get(i), actualTemplates.get(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

