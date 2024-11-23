
package _4Tipsy.TinyCloudCLI

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.eagerOption
import com.github.ajalt.mordant.rendering.TextColors.*

import com.github.ajalt.mordant.rendering.Theme
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.clikt.core.context

import java.io.File


// modules
import _4Tipsy.TinyCloudCLI.core.CookieHandler
import _4Tipsy.TinyCloudCLI.core.CacheHandler
import _4Tipsy.TinyCloudCLI.commands.userService.Login
import _4Tipsy.TinyCloudCLI.commands.userService.Who
import _4Tipsy.TinyCloudCLI.commands.userService.GetAvatar
import _4Tipsy.TinyCloudCLI.commands.fsService.Ls
import _4Tipsy.TinyCloudCLI.commands.fsService.Mkdir
import _4Tipsy.TinyCloudCLI.commands.fsService.Send
import _4Tipsy.TinyCloudCLI.commands.fsService.Rename
import _4Tipsy.TinyCloudCLI.commands.fsService.Delete
import _4Tipsy.TinyCloudCLI.commands.downloadService.Get
import _4Tipsy.TinyCloudCLI.commands.shareService.Share
import _4Tipsy.TinyCloudCLI.commands.shareService.Unshare
import _4Tipsy.TinyCloudCLI.commands.shareService.LsShared
import _4Tipsy.TinyCloudCLI.commands.other.FlushCookies
import _4Tipsy.TinyCloudCLI.commands.other.Preview
import _4Tipsy.TinyCloudCLI.commands.other.GetContents
import _4Tipsy.TinyCloudCLI.commands.other.SendDir
import _4Tipsy.TinyCloudCLI.util.Theming




class Tcc(t: Terminal) : CliktCommand(printHelpOnEmptyArgs = true) {
  override fun run() = Unit

  override fun aliases(): Map<String, List<String>> = Config().commandAliases

  init {
    context {
      terminal = t
      helpFormatter = { Theming(it) }
    }

    eagerOption("--version", "-v", help = "Print version") {
      println( blue("TinyCloud CLI v") + APP_VER )
      System.exit(0)
    }

    eagerOption("--aliases", "-a", help = "Print current command aliases") {
      Config().commandAliases
        .also {
          if (it.isEmpty()) {
            println(blue("Currently no aliases specified"))
            System.exit(0)
          }
        }
        .also { println(blue("Current aliases:")) }
        .forEach { k, v ->
          println("tcc ${blue(k)} ... = tcc ${blue(v.joinToString(" "))} ...")
        }
      System.exit(0)
    }
  }
}





val APP_VER = "1.0.0"
val PRODUCTION = true


val APP_LOC = if (PRODUCTION)
              Config.javaClass.protectionDomain.codeSource.location.path.let { File(it).parent }
              else "/home/qwerty/my-projects/tiny-cloud-cli/app"






fun main(args: Array<String>) {

  val theme = Theme {
    // Use ANSI-16 codes for help colors
    styles["info"] = green
    styles["warning"] = blue
    styles["danger"] = magenta
    styles["muted"] = gray

    // Remove the border around code blocks
    flags["markdown.code.block.border"] = false
  }


  // clear cache dir
  CacheHandler.clearCache()

  // life check config
  Config()

  // exec
  Tcc(Terminal(theme = theme))
    .subcommands(
      Login(), Who(), GetAvatar(),
      Ls(), Mkdir(), Send(), Rename(), Delete(),
      Get(),
      Share(), Unshare(), LsShared(),
      FlushCookies(), Preview(), GetContents()
    )
    .main(args)
}
