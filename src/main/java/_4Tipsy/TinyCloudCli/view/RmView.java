
package _4Tipsy.TinyCloudCli.view;



import java.net.ConnectException;


// modules
import _4Tipsy.TinyCloudCli.controllers.RmController;
import _4Tipsy.TinyCloudCli.models.RmRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.models.RmRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;
import _4Tipsy.TinyCloudCli.models.exceptions.ConfigException;






public class RmView {
  
  public static void viewRm(String entityName, String fullPath, String fileField, String entityType) {



    try {


      AnyResSuperClass parsedResponse = RmController.handleRm(entityName, fullPath, fileField, entityType);
      

      if (parsedResponse.getRespCode() == 200) {


        System.out.println("[200] Deleted: <%s> '%s'".formatted(entityType, fullPath));

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
