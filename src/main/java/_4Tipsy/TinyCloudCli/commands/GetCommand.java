
package _4Tipsy.TinyCloudCli.commands;



import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;


import _4Tipsy.TinyCloudCli.view.GetView;






@Command(
  name = "get",
  description = "Downloads files from your storage."
)
public class GetCommand implements Runnable {
  



  @Parameters(arity = "1..6", paramLabel = "<fileName>")
  String[] fileNames;


  @Option(names = {"-ff", "--file-field"}, defaultValue = "mere", description = "Could be 'mere'(by default), 'special' or 'temporary'.")
  String fileField;

  @Option(names = {"--from"}, description = "Path at your cloud", defaultValue = "/")
  String from;




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
        
        GetView.viewGet(fileName, fullPath, fileField);


      }
    }
  }

}
