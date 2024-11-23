
package _4Tipsy.TinyCloudCLI.util

import com.github.ajalt.mordant.rendering.TextColors.red
import java.awt.Toolkit

// modules
import _4Tipsy.TinyCloudCLI.Config



fun getTermSize(): Pair<Int, Int> {
  return _fallback() // i still have no idea how to implement this shit, so... default vars yeeeeeeeeeeeeeeeeeee!!!
}





private fun _fallback(): Pair<Int, Int> {
  return Pair(Config().defaultTermSize.width, Config().defaultTermSize.height)
}


