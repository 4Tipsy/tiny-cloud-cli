
package _4Tipsy.TinyCloudCLI.commands.userService


import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.mordant.rendering.TextColors.*

import io.ktor.client.request.get
import io.ktor.client.call.*
import io.ktor.http.*
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.*

import kotlinx.coroutines.runBlocking

import java.io.File


// modules
import _4Tipsy.TinyCloudCLI.ui.ErrorUI
import _4Tipsy.TinyCloudCLI.ui.printImage
import _4Tipsy.TinyCloudCLI.core.ReqClient
import _4Tipsy.TinyCloudCLI.core.getCookiesAsHeader
import _4Tipsy.TinyCloudCLI.core.CacheHandler
import _4Tipsy.TinyCloudCLI.Config



class GetAvatar : CliktCommand(help = "Get current user image") {


  // handler
  override fun run() {
    runBlocking {

      val client = ReqClient()
      val targetUrl = Config().main.apiRawUrl + "/user-service/get-user-image"

      val response = client.get(targetUrl) {
        headers.append(HttpHeaders.Cookie, getCookiesAsHeader("/"))
      }





      // if ok
      if (response.status.isSuccess()) {

        // download image file
        val userImgFile = File.createTempFile( "user_img_", ".tmp", File(CacheHandler.CACHE_DIR) )

        val fileChanel = response.body<ByteReadChannel>()
        while (!fileChanel.isClosedForRead) {
          val chunk = fileChanel.readRemaining(limit = Config().main.defaultChunkSize)
          while (!chunk.isEmpty) {
            userImgFile.appendBytes(chunk.readBytes())
          }
        }

        // show image
        printImage(userImgFile)
      }




      // if error
      else {
        ErrorUI(response)
      }


    }
  }



}


