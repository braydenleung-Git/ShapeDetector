import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.kotlin.KotlinModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class testCases {
    //set methods back to private later, there should have been something else calling it
    private final static String jsonFile = "./src/main/resources/testSolutions.json";

    public static void writeToJson(){
        final String directoryPath = "./src/main/resources/TestCases";
        File directory = new File(directoryPath);
        List<String[]> newlyAddedFiles = new ArrayList<>();
        List<String[]> probe = new ArrayList<>();
        boolean probe_bool = false;
        File[] files = directory.listFiles();
        //for each file within the file array
        probe_bool = files != null;
        if(probe_bool){
            for(File file : files){
                //if the file has an extension of .png
                if (file.getName().endsWith(".png")) {

                    //copy the relative path & file name(including extensions)
                    String relativePath = file.getAbsolutePath().substring(directoryPath.length() + 1);
                    String fileName = file.getName();
                    //add this object to the filepath and names
                    newlyAddedFiles.add(new String[]{relativePath, fileName});
                }
            }
        }
        try{
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new KotlinModule())
                    .enable(SerializationFeature.INDENT_OUTPUT);
            JsonNode rootNode = objectMapper.readTree(new FileInputStream(jsonFile));
            ArrayNode rootArrayNode = (ArrayNode) rootNode;
            boolean found = false;
            for (String[] filePathAndName : newlyAddedFiles) {
                for(JsonNode node : rootNode){
                    //if the current filename exist within the json mark it as "found"
                    probe_bool = node.get("FileName").asText().equals(filePathAndName[1]);
                    if (probe_bool){
                        found=true;
                        break;
                    }else{
                        found=false;
                    }
                }
                //if the file is not "found", then add the file to the json file
                if (!found){
                    ObjectNode addNode = objectMapper.createObjectNode();
                    addNode.put("Name",filePathAndName[1].substring(0,filePathAndName[1].length()-4));
                    addNode.put("FileName",filePathAndName[1]);
                    addNode.put("Path",filePathAndName[0].substring(directoryPath.length()-3));
                    rootArrayNode.add(addNode);
                    objectMapper.writeValue(new File(jsonFile), rootNode);

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
        int counter = 1;
        for(JsonNode currentNode: rootNode){
            listOfTestFile.add(counter+". "+ currentNode.get("Name").asText());
            counter++;
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