

package _4Tipsy.TinyCloudCli.models.commons;


import com.fasterxml.jackson.annotation.JsonProperty;






public class FsEntity {
  
  private String name;
  @JsonProperty("abs_path")
  private String absPath;
  @JsonProperty("base_type")
  private String baseType;
  @JsonProperty("size_in_mb")
  private double sizeInMb;
  @JsonProperty("mime_type")
  private String mimeType;



  public String getName() {
    return name;
  }
  public String getAbsPath() {
    return absPath;
  }
  public String getBaseType() {
    return baseType;
  }
  public String getMimeType() {
    return mimeType;
  }
  public double getSizeInMb() {
    return sizeInMb;
  }
}
