import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: varunhariharan
 * Date: 9/27/12
 * Time: 6:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class IRAssignment1 {
    static String path = "./data/";
    private static String string = "Modified_WithoutLines/";
    private static String modifiedPath = path + string;
    private static final String resultFile = "./Results.txt";
    static int NUMBER_OF_ROUNDS;

    public static void main(String[] args) {
        NUMBER_OF_ROUNDS = Integer.parseInt(args[0]);
        File directory = new File(modifiedPath);
        File[] files = directory.listFiles();
        List<Map<String, Integer>> tokenMapList = new ArrayList<Map<String, Integer>>();
        parseTokenizeAndCompile(files,tokenMapList);
        try {
            getStatistics(tokenMapList);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private static void parseTokenizeAndCompile(File[] files, List<Map<String, Integer>> tokenMapList) {
        for (File file : files) {
            if(!file.isDirectory()){
                String fileString = null;
                try {
                    fileString = FileUtils.readFileToString(file);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                Parser parser = new Parser();
                Map<String,Integer> tokenMap = parser.tokenize(fileString);
                tokenMap.keySet().toString();
                tokenMapList.add(tokenMap);
                System.out.println();   //do nothing. used for debugging
            }
        }
    }

    private static void getStatistics(List<Map<String, Integer>> tokenMapList) throws IOException {
        Map<String,Integer> overallTokenMap = new HashMap<String, Integer>();
        int numberOfTokens = 0;
        int numberOfTokensThatAppearOnlyOnce = 0;
        for (Map<String, Integer> tokenMap : tokenMapList) {
            mergeTokenMaps(overallTokenMap,tokenMap);

        }

        for (String token : overallTokenMap.keySet()) {
            int tokenCount = overallTokenMap.get(token);
            numberOfTokens += tokenCount;
            if (tokenCount == 1) {
                numberOfTokensThatAppearOnlyOnce++;
            }

        }

        int numberOfUniqueWords = overallTokenMap.size();
        String maxList = "";
        for (int index = 0; index < NUMBER_OF_ROUNDS; index++){
            int maxCount = -1;
            String maxToken = "";
            for (String token : overallTokenMap.keySet()) {
                int tokenCount = overallTokenMap.get(token);
                if(tokenCount > maxCount){
                    maxCount = tokenCount;
                    maxToken = token;
                }
            }
            maxList += (index+1)+".\t"+maxToken + "\t--->\t" + maxCount +"\n";
            overallTokenMap.remove(maxToken);
        }

        double averageNumberOfWordsPerDocument = (double)numberOfTokens/tokenMapList.size();
        String fileString = "Number of tokens:\t\t\t\t" + numberOfTokens+ "\nNumber of unique words:\t\t\t" + numberOfUniqueWords+ "\nNumber of tokens that appear only once\t" + numberOfTokensThatAppearOnlyOnce+ "\nAverage number of words per document\t\t" + averageNumberOfWordsPerDocument+ "\n" + "\nTop "+NUMBER_OF_ROUNDS+" tokens in database\n"+maxList;
        File statisticsFile = new File(resultFile);
        FileUtils.writeStringToFile(statisticsFile,fileString);
    }

    private static void mergeTokenMaps(Map<String, Integer> overallTokenMap, Map<String, Integer> tokenMap) {
        for (String token : tokenMap.keySet()) {
            int count = 1;
            if(overallTokenMap.containsKey(token)){
                Integer tokenCount = tokenMap.get(token);
                count = overallTokenMap.get(token) + tokenCount;
            }
            overallTokenMap.put(token,count);
        }
    }
}
