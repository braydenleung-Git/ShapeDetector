import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class testCases {
    private final static String jsonFile = "testSolutions.json";
    private void writeToJson(){
        final String directoryPath = "./TestCases";
        File directory = new File(directoryPath);
        List<String[]> newlyAddedFiles = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            //for each file within the file array
            for (File file : files) {
                //if it is a file, has an extension of .png
                if (file.isFile()&&file.getName().substring(file.getName().length()-3).equals(".png")) {
                    //copy the relative path & file name(including extensions)
                    String relativePath = file.getAbsolutePath().substring(directoryPath.length() + 1);
                    String fileName = file.getName();
                    //add this object to the filepath and names
                    newlyAddedFiles.add(new String[]{relativePath, fileName});
                }
            }
        }
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonFile);
            ArrayNode rootArrayNode = (ArrayNode) rootNode;
            boolean found = false;
            for (String[] filePathAndName : newlyAddedFiles) {
                JsonNode fileNode = rootNode.get("FileName");
                for(JsonNode node : fileNode){
                    if (node.asText().equals(filePathAndName[1])){
                        fileNode = rootNode.get(filePathAndName[0]);
                        found=true;
                        break;
                    }else{
                        found=false;
                    }
                    break;
                }
                if (!found){
                    ObjectNode addNode = objectMapper.createObjectNode();
                    addNode.put("Name",filePathAndName[1].substring(0,filePathAndName[1].length()-4));
                    addNode.put("FileName",filePathAndName[1]);
                    addNode.put("Path",filePathAndName[0]);
                    rootArrayNode.add(addNode);
                    objectMapper.writeValue(new File("testSolutions.json"), fileNode);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private ArrayList<String> getItemList() throws IOException {
        File directory = new File(jsonFile);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(directory);
        ArrayList<String> listOfTestFile = new ArrayList<>();
        for(JsonNode currentNode: rootNode){
            listOfTestFile.add(currentNode.get("Name").asText());
        }
        return listOfTestFile;
    }
    private void getItemPath(){

    }
}


/*
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String relativePath = "path/to/directory";

        URL resource = Main.class.getClassLoader().getResource(relativePath);
        if (resource == null) {
            throw new IllegalArgumentException("Directory not found: " + relativePath);
        }

        File directory = new File(resource.getFile());

        List<String[]> filePathsAndNames = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String relativePath = file.getAbsolutePath().substring(directory.getAbsolutePath().length() + 1);
                    String fileName = file.getName();
                    filePathsAndNames.add(new String[]{relativePath, fileName});
                }
            }
        }

        for (String[] filePathAndName : filePathsAndNames) {
            System.out.println("Relative path: " + filePathAndName[0] + ", File name: " + filePathAndName[1]);
        }
    }
}
 */