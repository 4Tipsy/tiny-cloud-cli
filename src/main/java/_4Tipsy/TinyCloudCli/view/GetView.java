
package _4Tipsy.TinyCloudCli.view;


import java.io.File;


// modules
import _4Tipsy.TinyCloudCli.controllers.GetController;
import _4Tipsy.TinyCloudCli.models.GetRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.models.GetRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;



public class GetView {
  
  public static void viewGet(String fileName, String fullPath, String fileField) {



    try {


      AnyResSuperClass parsedResponse = GetController.handleGet(fileName, fullPath, fileField);
      

      if (parsedResponse.getRespCode() == 200) {

        System.out.println("[200] Downloaded \"%s\"".formatted( new File(fileName).getAbsolutePath() ));



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
