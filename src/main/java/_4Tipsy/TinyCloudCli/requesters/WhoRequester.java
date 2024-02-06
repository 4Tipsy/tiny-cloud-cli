
package _4Tipsy.TinyCloudCli.requesters;



import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import java.io.IOException;
import java.util.concurrent.TimeUnit;



// modules
import _4Tipsy.TinyCloudCli.Config;
import _4Tipsy.TinyCloudCli.models.exceptions.ConfigException;





public class WhoRequester {
  

  public static Response makeWhoReq() throws ConfigException, IOException {



    // building request
    OkHttpClient client = new OkHttpClient.Builder().connectTimeout( Config.getReqTimeoutInSec(), TimeUnit.SECONDS ).build();

    String targetUrl = Config.getApiRawUrl() + "/api/user-service/get-current-user";
    Request.Builder req = new Request.Builder()
                              .url(targetUrl);
                              



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
