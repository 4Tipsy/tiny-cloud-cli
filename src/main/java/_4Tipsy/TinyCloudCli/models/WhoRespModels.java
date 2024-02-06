
package _4Tipsy.TinyCloudCli.models;




import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;
import _4Tipsy.TinyCloudCli.models.commons.UserInResp;


public class WhoRespModels {


  public static class OkResponseModel extends AnyResSuperClass {

    
    private UserInResp user;
    public UserInResp getUser() {
      return user;
    }


  }



  public static class ErrResponseModel extends AnyResSuperClass {
    

    private String error;

    public String getError() {
      return error;
    }
  }
}