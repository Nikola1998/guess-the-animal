package animals.utilities;

import animals.main.AnimalTreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;

public class Mapper {
    private final ObjectMapper objectMapper;
    private String fileName = "animals.json";


    public Mapper(String fileExtension) {
        this.fileName = "animals." + fileExtension;
        if (fileExtension.equals("xml")) {
            objectMapper = new XmlMapper();
        } else if (fileExtension.equals("yaml")) {
            objectMapper = new YAMLMapper();
        }
        else objectMapper = new JsonMapper();
    }
    public Mapper() {
        objectMapper = new JsonMapper();
    }



    public void mapperMap(AnimalTreeNode root) {
        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new File(fileName), root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AnimalTreeNode readFile() {
        try {
            return objectMapper.readValue(new File(fileName), AnimalTreeNode.class);
        } catch (IOException e) {
            System.out.println("No file was loaded.");
            //throw new RuntimeException(e);
            return null;
        }
    }
}
