
package _4Tipsy.TinyCloudCli;



import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;



import _4Tipsy.TinyCloudCli.Main;







public class Config {


  private static String _getPathToConfig() {

    String pathToConfig = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent() + File.separator + "config.json";

    
    // cuz if run via mvn pathToConfig=".../target/classes/config.json"
    pathToConfig = "config.json"; // 4 TESTING!!!


    return pathToConfig;
  }



  private static JsonNode _getConfigJsonNode() {
    try {

      String pathToConfig = _getPathToConfig();
      ObjectMapper objectMapper = new ObjectMapper();

      JsonNode configNode = objectMapper.readTree(new File(pathToConfig));
      return configNode;


    } catch (IOException e) {
      // TODO: handle exception

      e.printStackTrace();
      return null;
    }
  }






  public static String getApiRawUrl() {
    JsonNode node = _getConfigJsonNode();
    // check if field exists
    if (node.has("apiRawUrl")) {

    }
    return node.get("apiRawUrl").asText();
  }


  public static String getAToken() {
    JsonNode node = _getConfigJsonNode();
    // check if field exists
    if (node.has("aToken")) {

    }
    return node.get("aToken").asText();
  }


  




  public static void setNewAToken(String newAToken) {

    // open file
    // check if field exists
    JsonNode node = _getConfigJsonNode();
    if (node.has("aToken")) {

    }

    // make change
    ((ObjectNode)node).put("aToken", newAToken);


    // write to file
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(_getPathToConfig()), node); // write

    } catch (IOException e) {
      // TODO: handle exception


    }
  }



  
}