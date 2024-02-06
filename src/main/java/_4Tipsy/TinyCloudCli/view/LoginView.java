
package _4Tipsy.TinyCloudCli.view;



import java.net.ConnectException;


// modules
import _4Tipsy.TinyCloudCli.controllers.LoginController;
import _4Tipsy.TinyCloudCli.models.LoginRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.models.LoginRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;
import _4Tipsy.TinyCloudCli.models.exceptions.ConfigException;




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
      








    } catch (ConfigException e) {
      // if invalid config
      System.out.println("[ConfigException] %s".formatted(e.getMessage()));
    
    } catch (ConnectException e) {
      // if not responding
      System.out.println("[ConnectException] %s".formatted(e.getMessage()));

    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }
}
