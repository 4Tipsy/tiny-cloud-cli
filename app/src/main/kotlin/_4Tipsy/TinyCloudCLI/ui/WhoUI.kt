
package _4Tipsy.TinyCloudCLI.ui

import com.github.ajalt.mordant.rendering.TextColors.*
import java.text.DecimalFormat

// modules
import _4Tipsy.TinyCloudCLI.models.User
import _4Tipsy.TinyCloudCLI.util.prettifySize


class WhoUI (private val user: User) {
  init {

    val _perc = ( user.spaceUsed.toDouble() / user.totalSpaceAvailable.toDouble() * 100 )
      .let { DecimalFormat("#.##").format(it) } // round to #.##

    println()
    println(blue("Who: ") + user.name)
    println(blue("Email: ") + user.email)
    println(blue("Space used: ") + prettifySize(user.spaceUsed) + " (${gray(_perc)}%)")
    println(blue("Space total: ") + prettifySize(user.totalSpaceAvailable))
    println()
    println(gray("uid: ${user.uid}"))

  }
}