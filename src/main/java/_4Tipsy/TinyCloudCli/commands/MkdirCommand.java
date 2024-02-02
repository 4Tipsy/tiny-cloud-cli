
package _4Tipsy.TinyCloudCli.commands;



import _4Tipsy.TinyCloudCli.view.MkdirView;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;


@Command(
  name = "mkdir",
  description = "Creates new folder at your cloud."
)
public class MkdirCommand implements Runnable {
  


  @Parameters(arity = "1..6", paramLabel = "<folderName>")
  String[] folderNames;


  @Option(names = "--where", description = "Where to create new folder.",
  defaultValue = "/")
  String where;

  
  @Option(names = {"-ff", "--file-field"}, description = "Could be 'mere'(by default), 'special' or 'temporary'.",
  defaultValue = "mere")
  String fileField;




  @Override
  public void run() {
    

    // validation
    if ( !where.startsWith("/") ) {
      System.out.println("[Bad input] --where accepts absolute path as an argument.");
      System.out.println("Maybe you meant '%s'?".formatted("/"+where) );


    } else if ( !fileField.matches("mere|special|temporary") ) {
      System.out.println("[Bad input] --file-field accepts only 'mere'(by default), 'special' or 'temporary'.");


    }
    

    else { // if ok
      for (String folderName : folderNames) {

        String fullPath;
        if (where.equals("/")) {
          fullPath = "/" + folderName;
        } else {
          fullPath = where + "/" + folderName;
        }
        
        MkdirView.viewMkdir(folderName, fullPath, fileField);
      }
    }

  }
}
