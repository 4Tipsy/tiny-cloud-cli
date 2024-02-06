

package _4Tipsy.TinyCloudCli.controllers;



import java.io.IOException;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;


// modules
import _4Tipsy.TinyCloudCli.requesters.MkdirRequester;
import _4Tipsy.TinyCloudCli.models.MkdirRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.models.MkdirRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;
import _4Tipsy.TinyCloudCli.models.exceptions.ConfigException;





public class MkdirController {



  public static AnyResSuperClass handleMkdir(String folderName, String fullPath, String fileField) throws ConfigException, IOException {
    
    
    // make request
    Response response = MkdirRequester.makeMkdirReq(folderName, fullPath, fileField);


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
