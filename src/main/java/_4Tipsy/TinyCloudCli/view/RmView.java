
package _4Tipsy.TinyCloudCli.view;



// modules
import _4Tipsy.TinyCloudCli.controllers.RmController;
import _4Tipsy.TinyCloudCli.models.RmRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.models.RmRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;



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
      








    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }
}
