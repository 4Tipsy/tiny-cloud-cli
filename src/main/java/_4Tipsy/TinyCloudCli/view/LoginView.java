
package _4Tipsy.TinyCloudCli.view;



// modules
import _4Tipsy.TinyCloudCli.controllers.LoginController;
import _4Tipsy.TinyCloudCli.models.LoginRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.models.LoginRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;



public class LoginView {
  
  public static void viewLogin(String userEmail, String password) {



    try {


      AnyResSuperClass parsedResponse = LoginController.handleLogin(userEmail, password);
      

      if (parsedResponse.getRespCode() == 200) {

        System.out.println("Login successfully ^^");

      } else {

        ErrResponseModel errorResponse = (ErrResponseModel)parsedResponse;
        System.out.println("[%s] Error: %s".formatted(errorResponse.getRespCode(), errorResponse.getError()));

      }
      








    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }
}
