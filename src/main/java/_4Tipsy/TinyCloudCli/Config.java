
package _4Tipsy.TinyCloudCli;



import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;



import _4Tipsy.TinyCloudCli.Main;
import _4Tipsy.TinyCloudCli.models.exceptions.ConfigException;






public class Config {


  private static String _getPathToConfig() {

    String pathToConfig = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent() + File.separator + "config.json"; // path to this .jar dir

    
    // cuz if run via mvn pathToConfig=".../target/classes/config.json"
    pathToConfig = "config.json"; // 4 TESTING!!!


    return pathToConfig;
  }



  private static JsonNode _getConfigJsonNode() throws ConfigException {
    try {

      String pathToConfig = _getPathToConfig();
      ObjectMapper objectMapper = new ObjectMapper();

      JsonNode configNode = objectMapper.readTree(new File(pathToConfig));
      return configNode;


    } catch (IOException e) {
      throw new ConfigException("Can't find valid \"config.json\" file in app's directory");
    }
  }








  /* ACCESSING CONFIG DATA */

  public static String getApiRawUrl() throws ConfigException{
    JsonNode node = _getConfigJsonNode();
    // check if field exists
    if (!node.has("apiRawUrl")) {
      throw new ConfigException("Can't access \"apiRawUrl\" field in config.json");
    }
    // check if field is valid
    if (!node.get("apiRawUrl").isTextual()) {
      throw new ConfigException("Field \"apiRawUrl\" in config.json should be [String]");
    }
    // return if ok
    return node.get("apiRawUrl").asText();
  }



  public static String getAToken() throws ConfigException{
    JsonNode node = _getConfigJsonNode();
    // check if field exists
    if (!node.has("aToken")) {
      throw new ConfigException("Can't access \"aToken\" field in config.json");
    }
    // check if field is valid
    if (!node.get("aToken").isTextual() && !node.get("aToken").isNull()) {
      throw new ConfigException("Field \"aToken\" in config.json should be [String] or [null]");
    }
    // return if ok
    if (node.get("aToken").isTextual()) {
      return node.get("aToken").asText();
    } else {
      return null;
    }
  }


  public static int getReqTimeoutInSec() throws ConfigException{
    JsonNode node = _getConfigJsonNode();
    // check if field exists
    if (!node.has("reqTimeoutInSec")) {
      throw new ConfigException("Can't access \"reqTimeoutInSec\" field in config.json");
    }
    // check if field is valid
    if (!node.get("reqTimeoutInSec").isInt()) {
      throw new ConfigException("Field \"reqTimeoutInSec\" in config.json should be [Integer]");
    }
    // return if ok
    return node.get("reqTimeoutInSec").asInt();
  }







  public static void setNewAToken(String newAToken) throws ConfigException {

    // open file
    // check if field exists
    JsonNode node = _getConfigJsonNode();
    if (!node.has("aToken")) {
      throw new ConfigException("Can't access \"aToken\" field in config.json");
    }

    // make change
    ((ObjectNode)node).put("aToken", newAToken);


    // write to file
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(_getPathToConfig()), node); // write


    } catch (IOException e) {
      e.printStackTrace();
    }
  }










  
}