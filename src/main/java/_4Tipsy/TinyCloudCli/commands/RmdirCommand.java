
package _4Tipsy.TinyCloudCli.commands;



import _4Tipsy.TinyCloudCli.view.RmView;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;


@Command(
  name = "rmdir",
  description = "Deletes folder at your cloud."
)
public class RmdirCommand implements Runnable {
  


  @Parameters(arity = "1..6", paramLabel = "<folderName>")
  String[] folderNames;


  @Option(names = "--from", description = "Where to delete folder.",
  defaultValue = "/")
  String from;

  
  @Option(names = {"-ff", "--file-field"}, description = "Could be 'mere'(by default), 'special' or 'temporary'.",
  defaultValue = "mere")
  String fileField;




  @Override
  public void run() {


    // validation
    if ( !from.startsWith("/") ) {
      System.out.println("[Bad input] --from accepts absolute path as an argument.");
      System.out.println("Maybe you meant '%s'?".formatted("/"+from) );


    } else if ( !fileField.matches("mere|special|temporary") ) {
      System.out.println("[Bad input] --file-field accepts only 'mere'(by default), 'special' or 'temporary'.");


    }



    else { // if ok
        for (String folderName : folderNames) {
        String fullPath;
        if (from.equals("/")) {
          fullPath = "/" + folderName;
        } else {
          fullPath = from + "/" + folderName;
        }

        RmView.viewRm(folderName, fullPath, fileField, "folder");
      }
    }
    
  }
}
