

package _4Tipsy.TinyCloudCli.controllers;



import java.io.IOException;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;


// modules
import _4Tipsy.TinyCloudCli.requesters.WhoRequester;
import _4Tipsy.TinyCloudCli.models.WhoRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.models.WhoRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;
import _4Tipsy.TinyCloudCli.models.exceptions.ConfigException;





public class WhoController {



  public static AnyResSuperClass handleWho() throws ConfigException, IOException {
    
    
    // make request
    Response response = WhoRequester.makeWhoReq(); // get req


    // parse response
    ObjectMapper objectMapper = new ObjectMapper();

    if (response.code() == 200) {

      // parse
      OkResponseModel parsedResponse = objectMapper.readValue(response.body().string(), OkResponseModel.class);
      parsedResponse.setRespCode(response.code());
      
      return parsedResponse;

    } else {

      // parse
      ErrResponseModel parsedResponse = objectMapper.readValue(response.body().string(), ErrResponseModel.class);
      parsedResponse.setRespCode(response.code());
      
      return parsedResponse;

    }

  }


  
}
