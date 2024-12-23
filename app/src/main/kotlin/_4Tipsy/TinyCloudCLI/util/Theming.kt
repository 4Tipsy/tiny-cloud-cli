/*
  Stolen from official clikt github :P
*/

package _4Tipsy.TinyCloudCLI.util



import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.HelpFormatter
import com.github.ajalt.clikt.output.MordantHelpFormatter
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.mordant.rendering.TextAlign
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.Theme
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.ColumnWidth
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.widgets.Panel







class Theming(context: Context) : MordantHelpFormatter(context) {
  // You can override which styles are used for each part of the output.
  // If you want to change the color of the styles themselves, you can set them in the terminal's
  override fun styleSectionTitle(title: String): String = theme.style("muted")(title)

  // Print section titles like "Options" instead of "Options:"
  override fun renderSectionTitle(title: String): String = title

  // Print metavars like INT instead of <int>
  override fun normalizeParameter(name: String): String {
    var _name = name.replace(Regex("[A-Z]"), { matchResult -> "_${matchResult.value}" })
    _name = "<"+_name+">"
    return _name
  }

  // Print option values like `--option VALUE instead of `--option=VALUE`
  override fun renderAttachedOptionValue(metavar: String): String = " $metavar"

  // Put each parameter section in its own panel
  override fun renderParameters(
    parameters: List<HelpFormatter.ParameterHelp>,
  ): Widget = verticalLayout {
    width = ColumnWidth.Expand()
    for (section in collectParameterSections(parameters)) {
      cell(
        Panel(
          section.content,
          section.title,
          expand = true,
          titleAlign = TextAlign.LEFT,
          borderStyle = theme.style("muted")
        )
      )
    }
  }
}