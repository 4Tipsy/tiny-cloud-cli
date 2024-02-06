
package _4Tipsy.TinyCloudCli.commands;



import _4Tipsy.TinyCloudCli.view.WhoView;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;


@Command(
  name = "who",
  description = "Shows current logged-in user."
)
public class WhoCommand implements Runnable {
  




  @Option(names = "--json", description = "Return raw json output (only if success)",
  defaultValue = "false")
  boolean isJsonOutput;




  @Override
  public void run() {



    WhoView.viewWho(isJsonOutput);

    
  }
}
