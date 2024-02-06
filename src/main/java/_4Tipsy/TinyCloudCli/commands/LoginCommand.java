
package _4Tipsy.TinyCloudCli.commands;



import picocli.CommandLine.Command;



// modules
import _4Tipsy.TinyCloudCli.view.LoginView;


@Command(
  name = "login"
)
public class LoginCommand implements Runnable {





  @Override
  public void run() {
    

    // take prompt
    String userEmail = System.console().readLine("Enter your [userEmail]: ");
    char[] password = System.console().readPassword("Enter your [password]: ");


    // run
    LoginView.viewLogin(userEmail, String.valueOf(password));

  }
}
