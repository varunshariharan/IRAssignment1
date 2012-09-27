import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
* Created with IntelliJ IDEA.
* User: varunhariharan
* Date: 9/27/12
* Time: 3:27 AM
* To change this template use File | Settings | File Templates.
*/
public class ParserTest {
    String path = "./data/";
    private String string = "Modified_WithoutLines/";
    private String modifiedPath = path + string;

    @Test
    public void testRemoveToken() throws Exception {
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (File file : files) {
            if(!file.isDirectory()){
                String fileString = FileUtils.readFileToString(file);
                Parser parser = new Parser();
                String newFileString = parser.removeTags(fileString);
                File outputFile = new File(modifiedPath + file.getName());
                FileUtils.writeStringToFile(outputFile,newFileString);
            }
        }
    }

    @Test
    public void testTokenize() throws IOException {
        File directory = new File(modifiedPath);
        File[] files = directory.listFiles();
        for (File file : files) {
            if(!file.isDirectory()){
                String fileString = FileUtils.readFileToString(file);
                Parser parser = new Parser();
                Map<String,Integer> tokenMap = parser.tokenize(fileString);
                tokenMap.keySet().toString();
            }
        }
    }
}
