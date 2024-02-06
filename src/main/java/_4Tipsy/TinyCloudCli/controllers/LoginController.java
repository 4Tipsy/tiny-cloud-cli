

package _4Tipsy.TinyCloudCli.controllers;


import java.io.IOException;
import java.net.HttpCookie;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

// modules
import _4Tipsy.TinyCloudCli.Config;
import _4Tipsy.TinyCloudCli.requesters.LoginRequester;
import _4Tipsy.TinyCloudCli.models.LoginRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.models.LoginRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;
import _4Tipsy.TinyCloudCli.models.exceptions.ConfigException;







public class LoginController {

  
  public static AnyResSuperClass handleLogin(String userEmail, String password) throws ConfigException, IOException {
    


    // make request
    Response response = LoginRequester.makeLoginReq(userEmail, password);
    


    // parse response
    ObjectMapper objectMapper = new ObjectMapper();

    if (response.code() == 200) {

      // parse
      OkResponseModel parsedResponse = objectMapper.readValue(response.body().string(), OkResponseModel.class);
      parsedResponse.setRespCode(response.code());


      // save aToken
      String setCookieHeader = response.headers().get("Set-cookie");
    
      for (HttpCookie cookie : HttpCookie.parse(setCookieHeader)) {
  
        if (cookie.getName().equals("a-token")) {
          Config.setNewAToken(cookie.getValue()); // set new aToken
        }
      }
      
      return parsedResponse;

    } else {

      // parse
      ErrResponseModel parsedResponse = objectMapper.readValue(response.body().string(), ErrResponseModel.class);
      parsedResponse.setRespCode(response.code());
      return parsedResponse;

    }



  }
}
