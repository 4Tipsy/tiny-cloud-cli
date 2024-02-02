
package _4Tipsy.TinyCloudCli.commands;



import _4Tipsy.TinyCloudCli.view.SendView;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;
import java.nio.file.Paths;
import java.nio.file.Path;



@Command(
  name = "send",
  description = "Sends files to your cloud."
)
public class SendCommand implements Runnable {
  



  @Parameters(arity = "1..6", paramLabel = "<fileLocalPath>")
  String[] localFilePaths;


  @Option(names = {"-ff", "--file-field"}, defaultValue = "mere", description = "Could be 'mere'(by default), 'special' or 'temporary'.")
  String fileField;

  @Option(names = "--to", description = "Where to leave your files (path to folder at your cloud).", defaultValue = "/")
  String to;




  @Override
  public void run() {
    
    // validation
    if ( !to.startsWith("/") ) {
      System.out.println("[Bad input] --where accepts absolute path as an argument.");
      System.out.println("Maybe you meant '%s'?".formatted("/"+to) );


    } else if ( !fileField.matches("mere|special|temporary") ) {
      System.out.println("[Bad input] --file-field accepts only 'mere'(by default), 'special' or 'temporary'.");


    }
    

    else { // if ok

      for (String localFilePathString : localFilePaths) {


        // check if file exists on local machine
        Path localFilePath = Paths.get(localFilePathString);
        if (localFilePath.toFile().exists()) {


          String fullPath;
          if (to.equals("/")) {
            fullPath = "/" + localFilePath.getFileName();
          } else {
            fullPath = to + "/" + localFilePath.getFileName();
          }
          
          SendView.viewSend(localFilePath, fullPath, fileField);


        } else {
          System.out.println("[Bad input] File \"%s\" does not exist on this machine".formatted(localFilePath));
        }

      }
    }
  }
}
