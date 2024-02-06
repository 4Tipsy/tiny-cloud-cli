
package _4Tipsy.TinyCloudCli.commands;



import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;


import _4Tipsy.TinyCloudCli.view.MkdirView;
import _4Tipsy.TinyCloudCli.models.exceptions.BadInputException;




@Command(
  name = "mkdir",
  description = "Creates new folder at your cloud."
)
public class MkdirCommand implements Runnable {
  


  @Parameters(arity = "1..6", paramLabel = "<folderName>")
  String[] folderNames;


  @Option(names = {"-p"}, defaultValue = "mere:/", description = "Path at your cloud. Takes \"{fileField}:{AbsPath}\" value. {fileField} can be mere/special/temporary (m/s/t)")
  String path_;




  @Override
  public void run() {
    try {
    
      // validation!!!
      String[] pathSplitted = path_.split(":", 2);


      // if no ":" in path_
      if (pathSplitted.length != 2) {
        throw new BadInputException("-p takes \"{fileField}:{absPath}\" value. {fileField} was not given.");
      }

      // if fileField is invalid
      if ( !pathSplitted[0].matches("mere|special|temporary|m|s|t") ) {
        throw new BadInputException("{fileField} can be only mere/special/temporary (m/s/t), but \"%s\" was given.".formatted(pathSplitted[0]) );
      }
      
      // if path is not abs
      if ( !pathSplitted[1].startsWith("/") ) {
        throw new BadInputException("{absPath} should be absolute, but \"%s\" was given.".formatted(pathSplitted[1]) );
      }



      // if validation is ok, EXECUTING!!!
      for (String folderName : folderNames) {


        String fullPath;
        if (pathSplitted[1].equals("/")) {
          fullPath = "/" + folderName;
        } else {
          fullPath = pathSplitted[1] + "/" + folderName;
        }

        String fileField = pathSplitted[0];
        if (pathSplitted[0].equals("m")) { fileField = "mere"; }
        if (pathSplitted[0].equals("s")) { fileField = "special"; }
        if (pathSplitted[0].equals("t")) { fileField = "temporary"; }


        MkdirView.viewMkdir(folderName, fullPath, fileField);
      }
      
    

    // on bad user input
    } catch (BadInputException e) {
      System.out.println("[BadInput] %s".formatted(e.getMessage()));
    }
  }


  
}
