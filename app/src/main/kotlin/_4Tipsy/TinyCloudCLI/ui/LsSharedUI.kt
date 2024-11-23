
package _4Tipsy.TinyCloudCLI.ui

import com.github.ajalt.mordant.rendering.TextColors.*

// modules
import _4Tipsy.TinyCloudCLI.models.FsEntityWithPath




class LsSharedUI (val contents: List<FsEntityWithPath>) {
  init {
    println(gray("\nTotal shared entities: ${contents.size}"))
    contents.forEach {
      println("${it._pseudoFsPath} = '${blue(it.sharedLink!!)}'")
    }
  }
}