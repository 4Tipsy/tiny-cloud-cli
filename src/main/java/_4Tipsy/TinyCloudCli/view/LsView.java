
package _4Tipsy.TinyCloudCli.view;



import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.text.DecimalFormat;


// modules
import _4Tipsy.TinyCloudCli.models.LsRespModels.OkResponseModel;
import _4Tipsy.TinyCloudCli.controllers.LsController;
import _4Tipsy.TinyCloudCli.models.LsRespModels.ErrResponseModel;
import _4Tipsy.TinyCloudCli.models.commons.AnyResSuperClass;
import _4Tipsy.TinyCloudCli.models.commons.FsEntity;
import _4Tipsy.TinyCloudCli.utils.GetTerminalSize;





public class LsView {
  


  public static void viewLs (String pathToLayer, String fileField, boolean isJsonOutput) {

    

    final boolean SORT_BY_BASE_TYPE = true; // folders first, files last






    try {

      // get response
      AnyResSuperClass parsedResponse = LsController.handleLs(pathToLayer, fileField);
      

      // if ok
      if (parsedResponse.getRespCode() == 200) {


        // layer var
        FsEntity[] fsLayer = ((OkResponseModel)parsedResponse).getFsLayer();


        // sort
        if (SORT_BY_BASE_TYPE) {
          Arrays.sort(fsLayer, (entity1, entity2) -> {
            String baseType1 = entity1.getBaseType();
            String baseType2 = entity2.getBaseType();
        
            if (baseType1.equals("folder") && baseType2.equals("file")) {
              return -1;
            } else if (baseType1.equals("file") && baseType2.equals("folder")) {
              return 1;
            } else {
              return 0;
            }
          });
        }


        // pretty print
        if (isJsonOutput == false) {
          int termSize = GetTerminalSize.getTerminalSize();
          _printLsTableHeader(termSize, pathToLayer, fileField);
          
          if (fsLayer.length == 0) {
            System.out.println("<EMPTY>");

          } else {
            for (FsEntity fsEntity : fsLayer) {
              _printLsTableRow(termSize, fsEntity);
            }
          }


        
        // print raw json
        } else {
          String toPrint = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(fsLayer);
          System.out.println("%s: \"%s\":\n".formatted(fileField.toUpperCase(), pathToLayer));
          System.out.println(toPrint);
        }







      // on error response
      } else {

        ErrResponseModel errorResponse = (ErrResponseModel)parsedResponse;
        System.out.println("[%s] Error: %s".formatted(errorResponse.getRespCode(), errorResponse.getError()));

      }
      



    } catch (IOException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    
  }










  private static void _printLsTableRow(int termSize, FsEntity fsEntity) {
    
    String name = fsEntity.getName();
    if (name.length() > 21) {
      name = name.substring(0, 21) + "...";
    }
    String size;
    if (fsEntity.getBaseType().equals("folder")) {
      size = "<folder>";
    } else {
      double _size = fsEntity.getSizeInMb();
      size = new DecimalFormat("#.###").format(_size) + " MB";
    }

    int middleGapLength = termSize - name.length() - size.length() - 3;

    String toPrint =  name + " " + "_".repeat(middleGapLength) + " " + size;


    System.out.println(toPrint);
  }

  private static void _printLsTableHeader(int termSize, String pathToLayer, String fileField) {

    int middleGapLength = termSize - "name".length() - "size".length() - "where".length() - 4 - 2;
    String toPrint1 = " ".repeat(4) + "name" + " ".repeat(middleGapLength/2-1) + "where" + " ".repeat(middleGapLength/2+1) + "size" + " ".repeat(2);

    String title_ = String.format(" %s: \"%s\" ", fileField.toUpperCase(), pathToLayer);
    int gap_ = termSize - title_.length();
    String toPrint2 = "=".repeat(gap_ / 2) + title_ + "=".repeat(gap_ / 2);


    System.out.println(toPrint1);
    System.out.println(toPrint2);
  }


}
