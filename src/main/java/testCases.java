import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class testCases {
    //set methods back to private later, there should have been something else calling it
    private final static String jsonFile = "./src/main/resources/testSolutions.json";
    public static void writeToJson(){
        final String directoryPath = "./TestCases";
        File directory = new File(directoryPath);
        List<String[]> newlyAddedFiles = new ArrayList<>();
        List<String[]> probe = new ArrayList<>();

        File[] files = directory.listFiles();
        //for each file within the file array
        if(files != null){
            for (File file : files) {
                //if it is a file, has an extension of .png
                if (file.isFile()&&file.getName().substring(file.getName().length()-3).equals(".png")) {
                    //copy the relative path & file name(including extensions)
                    String relativePath = file.getAbsolutePath().substring(directoryPath.length() + 1);
                    String fileName = file.getName();
                    //add this object to the filepath and names
                    newlyAddedFiles.add(new String[]{relativePath, fileName});
                    probe = newlyAddedFiles;
                }
            }
        }
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new FileInputStream(jsonFile));
            ArrayNode rootArrayNode = (ArrayNode) rootNode;
            boolean found = false;
            for (String[] filePathAndName : newlyAddedFiles) {
                JsonNode fileNode = rootNode.get("FileName");
                for(JsonNode node : fileNode){
                    //if the current filename exist within the json mark it as "found"
                    if (node.asText().equals(filePathAndName[1])){
                        fileNode = rootNode.get(filePathAndName[0]);
                        found=true;
                        break;
                    }else{
                        found=false;
                    }
                    break;
                }
                //if the file is not "found", then add the file to the json file
                if (!found){
                    ObjectNode addNode = objectMapper.createObjectNode();
                    addNode.put("Name",filePathAndName[1].substring(0,filePathAndName[1].length()-4));
                    addNode.put("FileName",filePathAndName[1]);
                    addNode.put("Path",filePathAndName[0]);
                    rootArrayNode.add(addNode);
                    objectMapper.writeValue(new File(jsonFile), fileNode);

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<String> getItemList() throws IOException {
        File directory = new File(jsonFile);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(directory);
        ArrayList<String> listOfTestFile = new ArrayList<>();
        for(JsonNode currentNode: rootNode){
            listOfTestFile.add(currentNode.get("Name").asText());
        }
        return listOfTestFile;
    }
    public static File getItem(int index) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new FileInputStream(jsonFile));
        File file = null;
        for(JsonNode node : rootNode){
           if(node.get("Name").asText().equals(getItemList().get(index-1))){
               file = new File(node.get("Path").asText());
            }
        }
        return file;
    }
}