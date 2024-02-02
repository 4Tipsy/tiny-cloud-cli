

package _4Tipsy.TinyCloudCli.controllers;



import java.io.IOException;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;


// modules
import _4Tipsy.TinyCloudCli.requesters.LsRequester;
import _4Tipsy.TinyCloudCli.models.LsRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.models.LsRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;





public class LsController {



  public static AnyResSuperClass handleLs(String pathToLayer, String fileField) throws IOException {
    
    
    // make request
    Response response = LsRequester.makeLsReq(pathToLayer, fileField);


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
