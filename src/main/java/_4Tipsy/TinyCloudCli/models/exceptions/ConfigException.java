

package _4Tipsy.TinyCloudCli.models.exceptions;



import java.lang.Throwable;



// will be thrown when config is invalid or can't be found
public class ConfigException extends Throwable {
  

  public ConfigException(String message) { super(message); }

}