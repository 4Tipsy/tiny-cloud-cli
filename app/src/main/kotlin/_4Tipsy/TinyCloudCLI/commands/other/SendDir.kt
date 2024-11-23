
package _4Tipsy.TinyCloudCLI.commands.other


import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.default

import com.github.ajalt.mordant.rendering.TextColors.*

import io.ktor.http.*
import io.ktor.client.request.forms.*
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.*

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString

import java.io.File

// modules
import _4Tipsy.TinyCloudCLI.core.ReqClient
import _4Tipsy.TinyCloudCLI.core.getCookiesAsHeader
import _4Tipsy.TinyCloudCLI.core.getDirArchived
import _4Tipsy.TinyCloudCLI.ui.ErrorUI
import _4Tipsy.TinyCloudCLI.ui.ProgressBar
import _4Tipsy.TinyCloudCLI.util.validatePathOption
import _4Tipsy.TinyCloudCLI.Config



class SendDir : CliktCommand(help = "Upload directory (will archive it)") {
  val _dir by argument("dir")

  val path by option("-p", "--path", help = "Specify path to parent dir").default("drive:/")

  val autoShare by option("--auto-share", help = "Instantly makes entity shared (allowed only for singe send) (experimental)").flag(default = false)


  // models
  @Serializable
  data class RequestBody (
    val where: String,
    val name: String,
  )

  // handler
  override fun run() {
    runBlocking {

      val where = validatePathOption(path)
      val client = ReqClient()
      val targetUrl = Config().main.apiRawUrl + "/fs-service/upload-file"

      val dir = File(_dir)
      if (!dir.exists()) throw PrintMessage(red("Error: ") + "File '$_dir' does not exist")
      if (!dir.isDirectory) throw PrintMessage(red("Error: ") + "Entity '$_dir' is not a directory")

      val _name = dir.name + ".zip"
      val requestBody = RequestBody(
        where,
        _name
      )

      // send!
      val arcFile = getDirArchived(dir)
      val _mimeType = arcFile.toURL().openConnection().getContentType()

      val progress = ProgressBar(
        title = _name,
        total = arcFile.totalSpace,
      )
      val response = client.post(targetUrl) {
        setBody(MultiPartFormDataContent(
          formData {
            append("Request", Json.encodeToString(requestBody))
            append("File", arcFile.readBytes(), Headers.build {
              append(HttpHeaders.ContentDisposition, "filename=${_name.quote()}")
              append(HttpHeaders.ContentType, _mimeType)
            })
          },
          boundary = "WebAppBoundary"
        ))
        onUpload { bytesSentTotal, contentLength ->
          progress.total = contentLength
          progress.update(bytesSentTotal)
        }

        headers.append(HttpHeaders.Cookie, getCookiesAsHeader("/"))
      }


      if (!response.status.isSuccess()) {
        progress.closeErr()
        ErrorUI(response)

      // if it was ok
      } else {
        progress.closeOk()
        println(green("Ok [${brightWhite("200")}]: ") + "no errors")
        .also {
          if (autoShare) { println(yellow("Performing APP recall!")); _4Tipsy.TinyCloudCLI.main(arrayOf("share", _dir, "-p", path)) } // i know it's shitty approach
        }

      }

    }



  }

}