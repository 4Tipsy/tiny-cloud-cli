
package _4Tipsy.TinyCloudCli.view;



import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.DecimalFormat;
import java.net.ConnectException;


// modules
import _4Tipsy.TinyCloudCli.controllers.WhoController;
import _4Tipsy.TinyCloudCli.models.WhoRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.models.WhoRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;
import _4Tipsy.TinyCloudCli.models.commons.UserInResp;
import _4Tipsy.TinyCloudCli.models.exceptions.ConfigException;





public class WhoView {
  


  public static void viewWho (boolean isJsonOutput) {






    try {

      // get response
      AnyResSuperClass parsedResponse = WhoController.handleWho();
      

      // if ok
      if (parsedResponse.getRespCode() == 200) {


        // layer var
        UserInResp user = ((OkResponseModel)parsedResponse).getUser();




        // pretty print
        if (isJsonOutput == false) {

          String usedSpaceFormatted = new DecimalFormat("#.##").format(user.getUsedSpace());
          String availableSpaceFormatted = new DecimalFormat("#.##").format(user.getAvailableSpace());
          String availableSpaceInPerc = new DecimalFormat("#.##").format(
                                                                  user.getUsedSpace() / user.getAvailableSpace()
                                                                  * 100
                                                                  );

          System.out.println("Current user: \"%s\" (%s) [%s]".formatted(user.getUserName(), user.getUserEmail(),
          user.getIsVerified() ? "verified" : "unverified" ));
          System.out.println("Used space: %s/%s MB (%s)".formatted(usedSpaceFormatted, availableSpaceFormatted, availableSpaceInPerc+"%"));


        
        // print raw json
        } else {
          String rawJsonRes = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(user);
          System.out.println("Current user:\n");
          System.out.println(rawJsonRes);
        }







      // on error response
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