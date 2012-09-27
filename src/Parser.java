import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: varunhariharan
 * Date: 9/27/12
 * Time: 3:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class Parser {
    public String removeTags(String inputString){
        String returnString = inputString.replaceAll("<.*>","").replaceAll("\n"," ").replaceAll("\t"," ");
        return returnString;
    }

    public Map<String,Integer> tokenize(String fileString){
        String[] tokens = fileString.split(" ");
        Map<String,Integer> tokenMap = new HashMap<String, Integer>();
        for (String token : tokens) {
            if(!token.equals("")){
                int count = 1;
                if(tokenMap.containsKey(token)) {
                    count = tokenMap.get(token)+1;
                }
                tokenMap.put(token,count);
            }
        }
        System.out.println(tokenMap.toString());
        return tokenMap;
    }
}
