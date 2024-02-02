

package _4Tipsy.TinyCloudCli.controllers;



import java.io.IOException;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;


// modules
import _4Tipsy.TinyCloudCli.requesters.RmRequester;
import _4Tipsy.TinyCloudCli.models.RmRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.models.RmRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;





public class RmController {



  public static AnyResSuperClass handleRm(String entityName, String fullPath, String fileField, String entityType) throws IOException {
    
    
    // make request
    Response response = RmRequester.makeRmReq(entityName, fullPath, fileField, entityType);


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
