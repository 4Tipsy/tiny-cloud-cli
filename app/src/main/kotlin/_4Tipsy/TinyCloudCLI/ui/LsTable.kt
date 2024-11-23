
package _4Tipsy.TinyCloudCLI.ui

import com.github.ajalt.mordant.rendering.TextColors.*


// modules
import _4Tipsy.TinyCloudCLI.models.FsEntity
import _4Tipsy.TinyCloudCLI.util.getTermSize
import _4Tipsy.TinyCloudCLI.util.prettifySize



class LsTable(path: String, contents: List<FsEntity>) {
  init {
    val w = getTermSize().first



    fun printPath() {
      val _path = path.removePrefix("drive:")
      val _d = "drive: "
      println(blue(_d) + _path)
    }


    fun printContents() {
      // if empty
      if (contents.isEmpty()) {
        println("<Directory is empty>")
        return
      }
      // if not
      contents.forEach {

        val prettySize = if (it.size != null)
                         prettifySize(it.size)
                         else "<Dir>"

        val prettyName = if (it.name.length > 33)
                         it.name.substring(0, 30)+"..."
                         else it.name

        val sharedIco = if (it.isShared)
                        " <isShared>" // <-- ye, with " " before
                        else ""


        val _gw = w - prettyName.length - prettySize.length - sharedIco.length - 2
        val gap = ".".repeat(_gw)



        val _prettySize = if (prettySize=="<Dir>")
                          gray(prettySize) else prettySize
        println("${prettyName}${gray(sharedIco)} ${gray(gap)} $_prettySize")

      }
    }






    printPath()
    println( blue("=".repeat(w)) )
    printContents()
    println("\n")



  }



}