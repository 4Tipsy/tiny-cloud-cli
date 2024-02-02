

package _4Tipsy.TinyCloudCli.controllers;



import java.io.IOException;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;



// modules
import _4Tipsy.TinyCloudCli.requesters.SendRequester;
import _4Tipsy.TinyCloudCli.models.SendRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.models.SendRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;





public class SendController {



  public static AnyResSuperClass handleSend(Path localFilePath, String fullPath, String fileField) throws IOException {
    
    
    // make request
    Response response = SendRequester.makeSendReq(localFilePath, fullPath, fileField);


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
