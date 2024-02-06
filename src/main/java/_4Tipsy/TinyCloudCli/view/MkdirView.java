
package _4Tipsy.TinyCloudCli.view;



import java.net.ConnectException;


// modules
import _4Tipsy.TinyCloudCli.controllers.MkdirController;
import _4Tipsy.TinyCloudCli.models.MkdirRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.models.MkdirRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;
import _4Tipsy.TinyCloudCli.models.exceptions.ConfigException;







public class MkdirView {
  
  public static void viewMkdir(String folderName, String fullPath, String fileField) {



    try {


      AnyResSuperClass parsedResponse = MkdirController.handleMkdir(folderName, fullPath, fileField);
      

      if (parsedResponse.getRespCode() == 200) {

        System.out.println("[200] Created: <folder> '%s'".formatted(fullPath));

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
