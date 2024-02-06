
package _4Tipsy.TinyCloudCli.requesters;


import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.nio.file.Files;
import java.nio.file.Path;

import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import okhttp3.MultipartBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;



// modules
import _4Tipsy.TinyCloudCli.Config;
import _4Tipsy.TinyCloudCli.models.exceptions.ConfigException;
import _4Tipsy.TinyCloudCli.utils.NetProgressBar;





public class SendRequester {
  

  public static Response makeSendReq(Path localFilePath, String fullPath, String fileField) throws ConfigException, IOException {



    final Path _localFilePath = localFilePath; // idkwthhelpmeplzidontgetit
    final String _fileMimeType = Files.probeContentType(_localFilePath);



    RequestBody fileBody = new RequestBody() {

      @Override public MediaType contentType() {

        if (_fileMimeType == null) {
          return null;
        }
        return MediaType.parse(_fileMimeType);
      }
      

      @Override public long contentLength() {

        return _localFilePath.toFile().length();
      }

      @Override public void writeTo(BufferedSink sink) throws IOException {
        try (Source src = Okio.source(_localFilePath)) {

          long total = 0;
          long read;
          int _pbStep = 0;
          long buffSize = 131_072; // 128 kb
          System.out.println(""); // pretty print <3
          
          while((read = src.read(sink.buffer(), buffSize)) != -1) {
            total += read;
            sink.flush();
            _pbStep += 1;
            
            double howManyPercents = (double)total / contentLength() * 100;
            
            if (_pbStep == 3000) {
              NetProgressBar.print("Sending \"%s\"".formatted( _localFilePath.getFileName().toString()), contentLength(), howManyPercents, false);
              _pbStep = 0;
            }
          }
          NetProgressBar.print("Sending \"%s\"".formatted( _localFilePath.getFileName().toString()), contentLength(), 100.0, true);
        }
      }
    };








    // building json req body
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode requestBodyJson = objectMapper.createObjectNode();
    ObjectNode fsEntityJson = objectMapper.createObjectNode();
    fsEntityJson.put("name", _localFilePath.getFileName().toString());
    fsEntityJson.put("abs_path", fullPath);
    fsEntityJson.put("base_type", "file");
    requestBodyJson.set("fs_entity", fsEntityJson);
    requestBodyJson.put("file_field", fileField);

    String requestBodyJsonString = null;
    try {
      requestBodyJsonString = objectMapper.writeValueAsString(requestBodyJson);
    } catch (JsonProcessingException e) {}









    // multipart
    MultipartBody.Part filePart = MultipartBody.Part.createFormData("file",  _localFilePath.getFileName().toString(), fileBody);

    MultipartBody multipartBody = new MultipartBody.Builder()
                                      .setType(MultipartBody.FORM)
                                      .addFormDataPart("file",  _localFilePath.getFileName().toString(), filePart.body())
                                      .addFormDataPart("request", requestBodyJsonString)
                                      .build();





    // building request
    OkHttpClient client = new OkHttpClient.Builder().connectTimeout( Config.getReqTimeoutInSec(), TimeUnit.SECONDS ).build();

    String targetUrl = Config.getApiRawUrl() + "/api/fs-service/upload-file";
    Request.Builder req = new Request.Builder()
                              .url(targetUrl)
                              .post(multipartBody);
                              



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
