
package _4Tipsy.TinyCloudCLI.commands.downloadService


import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.default

import com.github.ajalt.mordant.rendering.TextColors.*

import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.call.*
import io.ktor.http.*
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes

import kotlinx.coroutines.runBlocking
import java.io.File

// models
import _4Tipsy.TinyCloudCLI.core.ReqClient
import _4Tipsy.TinyCloudCLI.core.getCookiesAsHeader
import _4Tipsy.TinyCloudCLI.ui.ErrorUI
import _4Tipsy.TinyCloudCLI.ui.ProgressBar
import _4Tipsy.TinyCloudCLI.util.validatePathOption
import _4Tipsy.TinyCloudCLI.Config



class Get : CliktCommand(help = "Download files") {
  val names by argument("name").multiple()

  val path by option("-p", "--path", help = "Specify path to parent dir").default("drive:/")
  val localTo by option("--local-to", "-t", help = "Where to save file locally (perhaps can fall with error if target is symlink, file and etc)").default("./")


  // handler
  override fun run() {
    runBlocking {

      var errors = listOf<HttpResponse>()

      val where = validatePathOption(path)
      val client = ReqClient()
      val targetUrl = Config().main.apiRawUrl + "/download-service/download"


      for (name in names) {

        val localFile = File(localTo + File.separator + name)
        if (localFile.exists()) {
          println(yellow("[TCC] File '$name' already exist! Skipped! ('${localFile.getAbsolutePath()}')"))
          continue
        }


        val progress = ProgressBar(
          title = "$name",
          total = 1,
        )
        val response = client.get(targetUrl) {
          url { parameters.append("target", "$where/$name") }
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