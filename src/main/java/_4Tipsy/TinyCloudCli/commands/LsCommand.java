
package _4Tipsy.TinyCloudCli.commands;



import _4Tipsy.TinyCloudCli.view.LsView;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;


@Command(
  name = "ls",
  description = "Shows contents of the folder at your cloud."
)
public class LsCommand implements Runnable {
  



  @Option(names = {"-w", "--where"}, description = "Path to folder you want to get list of. (default='/')",
  defaultValue = "/")
  String where;

  
  @Option(names = {"-ff", "--file-field"}, description = "Could be 'mere'(by default), 'special' or 'temporary'.",
  defaultValue = "mere")
  String fileField;


  @Option(names = "--json", description = "Return raw json output (only if success)",
  defaultValue = "false")
  Boolean isJsonOutput;




  @Override
  public void run() {

    // validation
    if ( !where.startsWith("/") ) {
      System.out.println("[Bad input] --where accepts absolute path as an argument.");
      System.out.println("Maybe you meant '%s'?".formatted("/"+where) );


    } else if ( !fileField.matches("mere|special|temporary") ) {
      System.out.println("[Bad input] --file-field accepts only 'mere'(by default), 'special' or 'temporary'.");


    }



    // if ok
    else {

      LsView.viewLs(where, fileField, isJsonOutput);

    }
  }
}
