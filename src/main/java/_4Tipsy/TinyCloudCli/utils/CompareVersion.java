

package _4Tipsy.TinyCloudCli.utils;






public class CompareVersion {

  
  public static boolean isNewVerHigher(String currentVersionString, String newVersionString) throws NumberFormatException {
    

    int[] currentVer = new int[3];
    int[] newVer = new int[3];

    // parse versions
    int idx = 0;
    for (String v : currentVersionString.split(".", 3)) {
      currentVer[idx] = Integer.parseInt(v);
      idx += 1;
    }
    idx = 0;
    for (String v : newVersionString.split(".", 3)) {
      newVer[idx] = Integer.parseInt(v);
      idx += 1;
    }


    // compare versions
    if (newVer[0] > currentVer[0]) { return true; }
    else if (newVer[1] > currentVer[1]) { return true; }
    else if (newVer[2] > currentVer[2]) { return true; }
    else { return false; }

    
  }
}
