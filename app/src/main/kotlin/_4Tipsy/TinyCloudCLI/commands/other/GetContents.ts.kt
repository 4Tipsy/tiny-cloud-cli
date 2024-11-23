
package _4Tipsy.TinyCloudCLI.commands.other


import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.default

import com.github.ajalt.mordant.rendering.TextColors.*

import io.ktor.client.statement.HttpResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import java.io.File

// models
import _4Tipsy.TinyCloudCLI.core.ReqClient
import _4Tipsy.TinyCloudCLI.core.getCookiesAsHeader
import _4Tipsy.TinyCloudCLI.ui.ErrorUI
import _4Tipsy.TinyCloudCLI.ui.ProgressBar
import _4Tipsy.TinyCloudCLI.util.validatePathOption
import _4Tipsy.TinyCloudCLI.Config
import _4Tipsy.TinyCloudCLI.models.FsEntity



class GetContents : CliktCommand(help = "Download ALL dir contents") {
  val name by argument("name")

  val path by option("-p", "--path", help = "Specify path to parent dir").default("drive:/")
  val localTo by option("--local-to", "-t", help = "Where to save file locally (perhaps can fall with error if target is symlink, file and etc)").default("./")


  // models
  @Serializable
  data class Request1Body (
    val where: String,
  )

  // handler
  override fun run() {
    runBlocking {

      var errors = listOf<HttpResponse>()

      val where = validatePathOption(path)
      val client = ReqClient()
      val targetUrl1 = Config().main.apiRawUrl + "/fs-service/get-dir-contents"
      val targetUrl2 = Config().main.apiRawUrl + "/download-service/download"


      // -- request 1 --
      val request1Body = Request1Body("$where/$name")
      val response1 = client.post(targetUrl1) {
        headers.append(HttpHeaders.Cookie, getCookiesAsHeader("/"))
        contentType(ContentType.Application.Json)
        setBody(request1Body)
      }
      if (!response1.status.isSuccess()) {
        ErrorUI(response1)
        System.exit(1) // EXIT
      }

      val contents = response1.body<List<FsEntity>>()
      for (fsEntity in contents) {

        val localFile = File(localTo + File.separator + fsEntity.name)
        if (localFile.exists()) {
          println(yellow("[TCC] File '${fsEntity.name}' already exist! Skipped! ('${localFile.getAbsolutePath()}')"))
          continue
        }


        val progress = ProgressBar(
          title = "${fsEntity.name}",
          total = 9999,
        )
        val response = client.get(targetUrl2) {
          url { parameters.append("target", "$where/$name/${fsEntity.name}") }
          headers.append(HttpHeaders.Cookie, getCookiesAsHeader("/"))
        }


        // if ok
        if (response.status.isSuccess()) {

          val _totalSize = response.headers.get(HttpHeaders.ContentLength)?.toLong()
          var _currentlyLoaded = 0L
          progress.total = _totalSize!!

          val stream: ByteReadChannel = response.body()
          while (!stream.isClosedForRead) {
            val chunk = stream.readRemaining(Config().main.defaultChunkSize)
            while (!chunk.isEmpty) {
              localFile.appendBytes(
                chunk.readBytes()
                  .also { _currentlyLoaded += it.size }
              )
              progress.update(_currentlyLoaded)
            }
          }

          progress.closeOk()
        }

        // if err
        else {
          errors = errors.plus(response)
          progress.closeErr()
        }
      }


      // show results
      if (errors.isEmpty()) {
        println( green("Ok [${brightWhite("200")}]: " + "no errors") )

      } else {
        errors.forEach {
          ErrorUI(it)
        }
      }


    }
  }

}