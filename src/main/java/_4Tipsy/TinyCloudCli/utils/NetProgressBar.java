
package _4Tipsy.TinyCloudCli.utils;


import java.lang.Math;
import java.text.DecimalFormat;


// modules
import _4Tipsy.TinyCloudCli.utils.GetTerminalSize;




public class NetProgressBar {



  public static void print(String info, long sizeInBytes, double howManyPercents, boolean close) {

    int termSize = GetTerminalSize.getTerminalSize();
    int howMuchProgressTiles = (int)Math.round(howManyPercents / 5);
    if (info.length() > 27) {
      info = info.substring(0, 27) + "...";
    }


    
    double sizeInMb = (double)sizeInBytes / 1024 / 1024;
    String sizeInMbFormatted = new DecimalFormat("#.###").format(sizeInMb);
    String howManyPercentsFormatted = new DecimalFormat("#.#").format(howManyPercents);
    String progressBlock = String.format("%s [%s%s] %s mb", howManyPercentsFormatted+"%", "#".repeat(howMuchProgressTiles), " ".repeat(20-howMuchProgressTiles), sizeInMbFormatted);




    int _gapSize = termSize - info.length() -progressBlock.length();

    String toPrint = info + " ".repeat(_gapSize) + progressBlock;



    if (close) {
      System.out.println(toPrint);
    } else {
      System.out.print(toPrint+"\r");
    }
  }
  
}
