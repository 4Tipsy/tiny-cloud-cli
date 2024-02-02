
package _4Tipsy.TinyCloudCli.commands;



import _4Tipsy.TinyCloudCli.view.RmView;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;


@Command(
  name = "rmfile",
  description = "Deletes file at your cloud."
)
public class RmfileCommand implements Runnable {
  


  @Parameters(arity = "1..6", paramLabel = "<fileName>")
  String[] fileNames;


  @Option(names = "--from", description = "Where to delete file.",
  defaultValue = "/")
  String from;

  
  @Option(names = {"-ff", "--file-field"}, description = "Could be 'mere'(by default), 'unmere' or 'reserved'.",
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
      for (String fileName : fileNames) {
        String fullPath;
        if (from.equals("/")) {
          fullPath = "/" + fileName;
        } else {
          fullPath = from + "/" + fileName;
        }
        
        RmView.viewRm(fileName, fullPath, fileField, "file");
      }
    }

    
  }
}
