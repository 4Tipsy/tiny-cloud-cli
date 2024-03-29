
package _4Tipsy.TinyCloudCli.requesters;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;



// modules
import _4Tipsy.TinyCloudCli.Config;
import _4Tipsy.TinyCloudCli.models.exceptions.ConfigException;





public class MkdirRequester {
  

  public static Response makeMkdirReq(String folderName, String fullPath, String fileField) throws ConfigException, IOException {



    // building json req body
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode requestBodyJson = objectMapper.createObjectNode();
    ObjectNode fsEntityJson = objectMapper.createObjectNode();
    fsEntityJson.put("name", folderName);
    fsEntityJson.put("abs_path", fullPath);
    fsEntityJson.put("base_type", "folder");
    requestBodyJson.set("fs_entity", fsEntityJson);
    requestBodyJson.put("file_field", fileField);

    String requestBodyJsonString = null;
    try {
      requestBodyJsonString = objectMapper.writeValueAsString(requestBodyJson);
    } catch (JsonProcessingException e) {}
     


    // building request body from json
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestBodyJsonString);


    // building request
    OkHttpClient client = new OkHttpClient.Builder().connectTimeout( Config.getReqTimeoutInSec(), TimeUnit.SECONDS ).build();

    String targetUrl = Config.getApiRawUrl() + "/api/fs-service/create-folder";
    Request.Builder req = new Request.Builder()
                              .url(targetUrl)
                              .post(requestBody);
                              



    // add aCookie
    String aToken = Config.getAToken();
    if (aToken != null) {
      req.addHeader("Cookie", "a-token="+aToken);
    }


    // executing request
    Response response = client.newCall(req.build()).execute();


    
    // returning result
    return response;
  }
  
}
