
package _4Tipsy.TinyCloudCLI.util


import com.github.ajalt.clikt.core.PrintMessage

import kotlin.jvm.Throws




@Throws(PrintMessage::class)
fun validatePathOption(_path: String): String {
  var path = _path



  // if without "drive:/"
  if (!path.startsWith("drive:")) path = "drive:$path"

  // validate
  if (!path.startsWith("drive:/")) PrintMessage("PATH should be absolute (start with '/')")



  return path
}