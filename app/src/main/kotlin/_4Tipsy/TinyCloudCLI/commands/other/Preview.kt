
package _4Tipsy.TinyCloudCLI.commands.other


import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.default

import com.github.ajalt.mordant.rendering.TextColors.*

import io.ktor.client.request.get
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
import _4Tipsy.TinyCloudCLI.core.CacheHandler
import _4Tipsy.TinyCloudCLI.ui.ErrorUI
import _4Tipsy.TinyCloudCLI.util.validatePathOption
import _4Tipsy.TinyCloudCLI.util.prettifySize
import _4Tipsy.TinyCloudCLI.ui.printImage
import _4Tipsy.TinyCloudCLI.Config



class Preview : CliktCommand(help = "Shows preview of image file (can fall with error, if target is not image)") {
  val name by argument("name")

  val path by option("-p", "--path", help = "Specify path to parent dir").default("drive:/")


  // handler
  override fun run() {
    runBlocking {

      val where = validatePathOption(path)
      val client = ReqClient()
      val targetUrl = Config().main.apiRawUrl + "/download-service/download"

      val localFile = File.createTempFile( "previewed_", ".tmp", File(CacheHandler.CACHE_DIR) )


      val response = client.get(targetUrl) {
        url { parameters.append("target", "$where/$name") }
        headers.append(HttpHeaders.Cookie, getCookiesAsHeader("/"))
      }


      // if ok
      if (response.status.isSuccess()) {

        val _totalSize = response.headers.get(HttpHeaders.ContentLength)?.toLong()
        if (_totalSize!! > Config().main.maxSizeToBePreviewed) throw PrintMessage(red("File is too large! (${prettifySize(_totalSize)}"))

        val stream: ByteReadChannel = response.body()
        while (!stream.isClosedForRead) {
          val chunk = stream.readRemaining(Config().main.defaultChunkSize)
          while (!chunk.isEmpty) {
            localFile.appendBytes(
              chunk.readBytes()
            )
          }
        }

        printImage(localFile)
      }

      // if err
      else {
        ErrorUI(response)
      }



    }
  }

}