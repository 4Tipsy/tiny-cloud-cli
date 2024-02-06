
package _4Tipsy.TinyCloudCli.requesters;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;



// modules
import _4Tipsy.TinyCloudCli.Config;
import _4Tipsy.TinyCloudCli.models.exceptions.ConfigException;







public class LoginRequester {
  



  public static Response makeLoginReq(String userEmail, String password) throws ConfigException, IOException {



    // building json req body
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode requestBodyJson = objectMapper.createObjectNode();
    requestBodyJson.put("user_email", userEmail);
    requestBodyJson.put("password", password);

    String requestBodyJsonString = null;
    try {
      requestBodyJsonString = objectMapper.writeValueAsString(requestBodyJson);
    } catch (JsonProcessingException e) {}


    // building request body from json
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestBodyJsonString);


    // building request
    OkHttpClient client = new OkHttpClient.Builder().connectTimeout( Config.getReqTimeoutInSec(), TimeUnit.SECONDS ).build();

    String targetUrl = Config.getApiRawUrl() + "/api/user-service/login";
    Request.Builder req = new Request.Builder()
                              .url(targetUrl)
                              .post(requestBody);




    // executing request
    Response response = client.newCall(req.build()).execute();



    // returning result
    return response;

  }
  
}

