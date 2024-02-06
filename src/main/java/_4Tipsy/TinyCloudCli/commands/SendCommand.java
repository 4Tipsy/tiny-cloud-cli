
package _4Tipsy.TinyCloudCli.commands;



import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;
import java.nio.file.Paths;
import java.io.File;
import java.nio.file.Path;



import _4Tipsy.TinyCloudCli.view.SendView;
import _4Tipsy.TinyCloudCli.models.exceptions.BadInputException;





@Command(
  name = "send",
  description = "Sends files to your cloud."
)
public class SendCommand implements Runnable {
  



  @Parameters(paramLabel = "<fileLocalPath>")
  String[] localFilePaths;


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
      for (String localFilePathString_ : localFilePaths) {
        Path localFilePath = Paths.get(localFilePathString_);



        // if file not exist on this machine, print error and skip it
        if ( !(new File(localFilePathString_).exists()) ) {
          System.out.println("[IOException] There is no \"%s\" file on this machine.".formatted(localFilePath.getFileName()) );
          continue; // skip this file
        }


        String fullPath;
        if (pathSplitted[1].equals("/")) {
          fullPath = "/" + localFilePath.getFileName();
        } else {
          fullPath = pathSplitted[1] + "/" + localFilePath.getFileName();
        }

        String fileField = pathSplitted[0];
        if (pathSplitted[0].equals("m")) { fileField = "mere"; }
        if (pathSplitted[0].equals("s")) { fileField = "special"; }
        if (pathSplitted[0].equals("t")) { fileField = "temporary"; }


        SendView.viewSend(localFilePath, fullPath, fileField);
      }
      
    

    // on bad user input
    } catch (BadInputException e) {
      System.out.println("[BadInput] %s".formatted(e.getMessage()));
    }
  }



}
