

package _4Tipsy.TinyCloudCli.controllers;



import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;


// modules
import _4Tipsy.TinyCloudCli.requesters.GetRequester;
import _4Tipsy.TinyCloudCli.models.GetRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.models.GetRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;
import _4Tipsy.TinyCloudCli.utils.NetProgressBar;
import _4Tipsy.TinyCloudCli.models.exceptions.ConfigException;






public class GetController {



  public static AnyResSuperClass handleGet(String fileName, String fullPath, String fileField) throws ConfigException, IOException {
    
    
    // make request
    Response response = GetRequester.makeGetReq(fileName, fullPath, fileField);


    // parse response
    ObjectMapper objectMapper = new ObjectMapper();

    if (response.code() == 200) {

      // DOWNLOAD FILE //
      long fileTotalSize = response.body().contentLength();
      InputStream fileInputStream = response.body().byteStream();



      // check if file with same name already exists
      if (new File(fileName).exists()) {
        System.out.println("[Err] File with same name already exists");

        return null;


      // if ok, then download
      } else {
        long downloadedSize = 0;
        byte[] buffer = new byte[4096];
        int bytesRead;

        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {

          System.out.println(""); // pretty print <3 
          while ((bytesRead = fileInputStream.read(buffer)) != -1) {

            outputStream.write(buffer, 0, bytesRead);
            downloadedSize += bytesRead;
            double howManyPercents = downloadedSize / (double)fileTotalSize * 100;
            NetProgressBar.print("Getting %s".formatted(fileName), fileTotalSize, howManyPercents, false);
          }
          NetProgressBar.print("Getting %s".formatted(fileName), fileTotalSize, 100.0, true);
        }

      }





      // return ok response mock
      AnyResSuperClass _response = objectMapper.readValue("{\"result\": \"success\"}", OkResponseModel.class);
      _response.setRespCode(200);
      return _response;

    } else {

      // parse
      ErrResponseModel parsedResponse = objectMapper.readValue(response.body().string(), ErrResponseModel.class);
      parsedResponse.setRespCode(response.code());
      
      return parsedResponse;

    }

  }


  
}
