
package _4Tipsy.TinyCloudCli.commands;



import picocli.CommandLine.Command;



// modules
import _4Tipsy.TinyCloudCli.Config;
import _4Tipsy.TinyCloudCli.models.exceptions.ConfigException;



@Command(
  name = "logout"
)
public class LogoutCommand implements Runnable {





  @Override
  public void run() {
    try {
    

      if (Config.getAToken() != null) {


        String input = System.console().readLine("You surely want to logout? (type \"yes\" to confirm): ");
        if (input.equals("yes")) {
          Config.setNewAToken(null); // ser aToken to null in config.json
          System.out.println("Logout successfully...");
        }


      } else {
        System.out.println("You are not logged-in, no need to logout...");
      }



    } catch (ConfigException e) {
      System.out.println("[ConfigException] %s".formatted(e.getMessage()));
    }
  }
}
