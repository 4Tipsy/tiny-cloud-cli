

package _4Tipsy.TinyCloudCli.models.exceptions;



import java.lang.Throwable;



// will be thrown mainly in commands if user input is invalid (mostly code sugar)
public class BadInputException extends Throwable {
  

  public BadInputException(String message) { super(message); }

}