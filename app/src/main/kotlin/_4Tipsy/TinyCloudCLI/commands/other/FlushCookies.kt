
package _4Tipsy.TinyCloudCLI.commands.other


import com.github.ajalt.clikt.core.CliktCommand

import java.io.File

// modules
import _4Tipsy.TinyCloudCLI.APP_LOC


class FlushCookies : CliktCommand(help = "Delete all cookies (will cause logout)") {


  override fun run() {

    // clear cookies
    println("Deleting 'cookies.toml'...")
    File( listOf(APP_LOC, "cookies.toml").joinToString(File.separator) )
      .delete()
  }
}