/*
* ChatGPT moment
*/
package _4Tipsy.TinyCloudCLI.ui

import com.github.ajalt.mordant.rendering.TextColors.red

import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

import _4Tipsy.TinyCloudCLI.Config






@Throws(IOException::class)
fun printImage(_originalImage: File) {

  var TERM_W_COMPUTED = org.jline.terminal.TerminalBuilder.terminal().width // !!!
  if (TERM_W_COMPUTED < 10) {
    println(red("Unable to calc actual term size, used default values!"))
    TERM_W_COMPUTED = Config().defaultTermSize.width
  }


  val image = getImgAdaptivelyResized(_originalImage, TERM_W_COMPUTED)


    .let { ImageIO.read(it) }




  val width = image.width
  val height = image.height

  for (y in 0 until height) {
    for (x in 0 until width) {
      val rgb = image.raster.getPixel(x, y, IntArray(3))
      print("\u001b[38;2;" + rgb[0] + ";" + rgb[1] + ";" + rgb[2] + "m" + "██")
    }
    println()
  }

}



@Throws(IOException::class)
private fun getImgAdaptivelyResized(inputFile: File, _desiredWidth: Int): File {
  val desiredWidth = _desiredWidth / 2 // as "pixel" is 2 symbols

  val originalImage = ImageIO.read(inputFile)
  val aspectRatio = originalImage.width.toDouble() / originalImage.height
  val desiredHeight = (desiredWidth / aspectRatio).toInt()
  val resizedImage = BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_RGB)
  val graphics2D = resizedImage.createGraphics()
  graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
  graphics2D.drawImage(originalImage, 0, 0, desiredWidth, desiredHeight, null)
  graphics2D.dispose()
  val outputFile = File(inputFile.parentFile, "resized_" + inputFile.name)
  ImageIO.write(resizedImage, "jpg", outputFile)

  return outputFile

}
