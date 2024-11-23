
package _4Tipsy.TinyCloudCLI.ui

import com.github.ajalt.mordant.rendering.TextColors.*
import kotlin.math.roundToInt

// modules
import _4Tipsy.TinyCloudCLI.util.getTermSize
import _4Tipsy.TinyCloudCLI.util.prettifySize


class ProgressBar (
  private var title: String,
  var total: Long,
  private var current: Long = 0,
) {

  init {
    render()
  }

  fun update(newCurrent: Long) {
    this.current = newCurrent
    render()
  }

  fun closeErr() {
    this.title = title
    render(close = true, state = "err")
    println()
  }

  fun closeOk() {
    this.title = title
    render(close = true, state = "ok")
    println()
  }



  private fun render(close: Boolean = false, state: String = "progress") {
    val _r = if (!close) "\r" else ""
    val w = getTermSize().first
    val progressIndicatorW = (w/2.5).roundToInt()
    val titleW = (w/3.0).roundToInt()


    val progressPercentage = current.toDouble() / total.toDouble() * 100
    val progressPercentagePretty = ""+(progressPercentage*100).roundToInt() / 100.0 // #.##


    // progress indicator inner
    val _progressInner_filled = (progressPercentage/100*progressIndicatorW).let { "=".repeat(it.roundToInt()) }
    val _progressInner_empty = (progressIndicatorW - _progressInner_filled.length).let { " ".repeat(it) }
    var progressInner = _progressInner_filled + _progressInner_empty

    // pretty size
    var prettySize = prettifySize(this.total)
    while (prettySize.length < 9) {
      prettySize = " " + prettySize
    }
    prettySize = "[$prettySize]"

    // pretty title
    val prettyTitle = if (title.length > titleW) {
      title.slice(0..(titleW-3)) + "..."
    } else {
      title
    }

    // gap
    val gw = w - prettyTitle.length - progressInner.length+2 - progressPercentagePretty.length+1 - prettySize.length - 4 - 2


    progressInner = when(state) {
      "ok" -> green(progressInner)
      "err" -> red(progressInner)
      else -> blue(progressInner)
    }
    print("${prettyTitle} ${" ".repeat(gw)} ${progressPercentagePretty}% [${progressInner}] ${prettySize}$_r")
  }



}