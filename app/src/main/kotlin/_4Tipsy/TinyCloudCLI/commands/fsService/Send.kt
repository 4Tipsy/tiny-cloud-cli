
package _4Tipsy.TinyCloudCLI.commands.fsService


import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.default

import com.github.ajalt.mordant.rendering.TextColors.*

import io.ktor.client.statement.HttpResponse
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



class Send : CliktCommand(help = "Upload files") {
  val files by argument("file").multiple()

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
      // 4 autoShare, cuz i don't wanna redo UI logic for this!
      if (autoShare && files.size != 1) {
        println(red("'--auto-share' allowed only when sending single entity"))
        System.exit(1)
      }


      var errors = listOf<HttpResponse>()

      val where = validatePathOption(path)
      val client = ReqClient()
      val targetUrl = Config().main.apiRawUrl + "/fs-service/upload-file"


      files.forEach { _file ->

        var file = File(_file)
        var fileName = file.name
        if (!file.exists()) throw PrintMessage(red("Error: ") + "File '$_file' does not exist")
        if (file.isDirectory) {
          file = getDirArchived(file)
          fileName += ".zip"
        }

        val _mimeType = file.toURL().openConnection().getContentType()

        val requestBody = RequestBody(
          where,
          fileName
        )

        // send!
        val progress = ProgressBar(
          title = fileName,
          total = file.totalSpace,
        )
        val response = client.post(targetUrl) {
          setBody(MultiPartFormDataContent(
            formData {
              append("Request", Json.encodeToString(requestBody))
              append("File", file.readBytes(), Headers.build {
                append(HttpHeaders.ContentDisposition, "filename=${fileName.quote()}")
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
          errors = errors.plus(response)
          progress.closeErr()


        // if it was ok
        } else {
          progress.closeOk()
          if (autoShare) { println(yellow("Performing APP recall!")); _4Tipsy.TinyCloudCLI.main(arrayOf("share", fileName, "-p", path)) } // i know it's shitty approach
        }

      }


      // show results
      if (errors.isEmpty() && !autoShare/*<- ui issues*/) {
        println( green("Ok [${brightWhite("200")}]: " + "no errors") )

      } else {
        errors.forEach {
          ErrorUI(it)
        }
      }

    }
  }
}