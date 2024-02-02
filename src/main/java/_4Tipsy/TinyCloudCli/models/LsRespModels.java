
package _4Tipsy.TinyCloudCli.models;

import com.fasterxml.jackson.annotation.JsonProperty;



import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;
import _4Tipsy.TinyCloudCli.models.commons.FsEntity;


public class LsRespModels {


  public static class OkResponseModel extends AnyResSuperClass {

    
    @JsonProperty("fs_layer")
    private FsEntity[] fsLayer;

    public FsEntity[] getFsLayer() {
      return fsLayer;
    }

  }



  public static class ErrResponseModel extends AnyResSuperClass {
    

    private String error;

    public String getError() {
      return error;
    }
  }
}