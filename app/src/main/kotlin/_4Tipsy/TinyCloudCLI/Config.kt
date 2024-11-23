
package _4Tipsy.TinyCloudCLI


import com.github.ajalt.clikt.core.PrintMessage

import kotlinx.serialization.Serializable
import net.peanuuutz.tomlkt.Toml
import kotlinx.serialization.decodeFromString

import kotlin.jvm.Throws
import java.io.File

// modules
import _4Tipsy.TinyCloudCLI.exceptions.InvalidConfigException
import _4Tipsy.TinyCloudCLI.APP_LOC



// model
@Serializable
data class ConfigModel(
  val main: Main,
  val defaultTermSize: DefaultTermSize,
  val commandAliases: Map<String, List<String>>,
  val experimental: Experimental,
) {
  @Serializable
  data class Main (
    val apiRawUrl: String,
    val requestTimeout: Int,
    val defaultChunkSize: Long,
    val maxSizeToBePreviewed: Long,
  )
  @Serializable
  data class DefaultTermSize (
    val width: Int,
    val height: Int,
  )
  @Serializable
  data class Experimental (
    val rememberLocationInX: Boolean,
  )
}





object Config {
  private var inst: ConfigModel? = null


  @Throws(InvalidConfigException::class)
  operator fun invoke(): ConfigModel {

    // load config
    if (this.inst == null) {

      var configFile = listOf(APP_LOC, "config.toml").joinToString(File.separator).let { File(it) }
      if (!configFile.exists()) {
        throw InvalidConfigException("'config.toml' file not exist")
      }

      this.inst = Toml.decodeFromString<ConfigModel>(configFile.readText())
    }

    return this.inst!!
  }
}