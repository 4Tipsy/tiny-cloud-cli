
package _4Tipsy.TinyCloudCli;


import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.IVersionProvider;



// modules
import _4Tipsy.TinyCloudCli.commands.WhoCommand;
import _4Tipsy.TinyCloudCli.commands.SendCommand;
import _4Tipsy.TinyCloudCli.commands.GetCommand;
import _4Tipsy.TinyCloudCli.commands.LsCommand;
import _4Tipsy.TinyCloudCli.commands.LoginCommand;
import _4Tipsy.TinyCloudCli.commands.LogoutCommand;
import _4Tipsy.TinyCloudCli.commands.MkdirCommand;
import _4Tipsy.TinyCloudCli.commands.RmdirCommand;
import _4Tipsy.TinyCloudCli.commands.RmfileCommand;



@Command(
  name = "tcc",
  subcommands = {WhoCommand.class, SendCommand.class, GetCommand.class, LsCommand.class, LoginCommand.class, LogoutCommand.class, MkdirCommand.class, RmdirCommand.class, RmfileCommand.class},
  description = "",

  versionProvider = _4Tipsy.TinyCloudCli.Main.VersionProvider.class,
  mixinStandardHelpOptions = true
)
public class Main {





  // some constants
  public static final String CLI_VERSION = "0.1.0";


  


  // help command
  @Option(names = {"-h", "--help"}, usageHelp = true, description = "Print information about commands and app itself and exit.")
  boolean helpRequested;


  // version command
  @Option(names = { "-v", "--version" }, versionHelp = true, description = "Print version information and exit.")
  boolean versionRequested;
  static class VersionProvider implements IVersionProvider {
    public String[] getVersion() {
      String willBePrinted = String.format("Tiny-Cloud-CLI@4Tipsy: @|yellow v%s|@ <3", CLI_VERSION );
      return willBePrinted.split("", 1);
    }
  }





  // RUN IT, BABE!!!
  public static void main(String[] args) {


    // 4 TESTING! // (btw check Config.java)
    String[] _args = {"logout"}; args = _args;
    


    int exitCode = new CommandLine(new Main()).execute(args);
    System.exit(exitCode);

  }
}