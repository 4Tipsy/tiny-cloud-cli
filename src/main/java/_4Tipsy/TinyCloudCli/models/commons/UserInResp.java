

package _4Tipsy.TinyCloudCli.models.commons;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;





public class UserInResp {
  
  
  @JsonProperty("verified")
  private boolean isVerified;
  @JsonProperty("user_id")
  private int userId;
  @JsonProperty("user_name")
  private String userName;
  @JsonProperty("user_email")
  private String userEmail;
  @JsonProperty("used_space")
  private double usedSpace;
  @JsonProperty("available_space")
  private double availableSpace;


  // not now
  @JsonIgnore
  private Object shared;
  



  public boolean getIsVerified() {
    return isVerified;
  }
  public int getUserId() {
    return userId;
  }
  public String getUserName() {
    return userName;
  }
  public String getUserEmail() {
    return userEmail;
  }
  public double getUsedSpace() {
    return usedSpace;
  }
  public double getAvailableSpace() {
    return availableSpace;
  }



}
