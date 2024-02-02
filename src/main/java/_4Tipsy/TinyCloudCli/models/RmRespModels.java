
package _4Tipsy.TinyCloudCli.models;



import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;




public class RmRespModels {


  public static class OkResponseModel extends AnyResSuperClass {
    

  }



  public static class ErrResponseModel extends AnyResSuperClass {
    

    private String error;

    public String getError() {
      return error;
    }
  }
}