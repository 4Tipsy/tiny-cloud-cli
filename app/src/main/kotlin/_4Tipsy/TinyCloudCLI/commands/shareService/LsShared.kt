
package _4Tipsy.TinyCloudCLI.commands.shareService


import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.flag

import io.ktor.client.request.get
import io.ktor.client.call.*
import io.ktor.http.*

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

import kotlinx.coroutines.runBlocking


// models
import _4Tipsy.TinyCloudCLI.core.ReqClient
import _4Tipsy.TinyCloudCLI.core.getCookiesAsHeader
import _4Tipsy.TinyCloudCLI.models.FsEntityWithPath
import _4Tipsy.TinyCloudCLI.ui.ErrorUI
import _4Tipsy.TinyCloudCLI.ui.LsSharedUI
import _4Tipsy.TinyCloudCLI.util.ContentSorter
import _4Tipsy.TinyCloudCLI.Config




class LsShared : CliktCommand(help = "Show all shared entities") {
  val json by option("--json", help = "Prints result as raw json").flag(default = false)


  // handler
  override fun run() {
    runBlocking {

      val client = ReqClient()
      val targetUrl = Config().main.apiRawUrl + "/share-service/get-all-shared-entities"


      val response = client.get(targetUrl) {
        headers.append(HttpHeaders.Cookie, getCookiesAsHeader("/"))
        contentType(ContentType.Application.Json)
      }


      // if ok
      if (response.status.isSuccess()) {

        val contentsUnsorted = response.body<List<FsEntityWithPath>>()
        val contents = ContentSorter.sort(contentsUnsorted)

        // print
        if (json) {
          val _Json = Json { prettyPrint = true }
          echo( _Json.encodeToString(contents) )

        } else {
          LsSharedUI(contents)
        }
      }



      // if error
      else {
        ErrorUI(response)
      }



    }
  }


}

