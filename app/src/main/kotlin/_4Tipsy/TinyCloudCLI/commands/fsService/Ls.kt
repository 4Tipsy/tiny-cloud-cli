
package _4Tipsy.TinyCloudCLI.commands.fsService


import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.call.*
import io.ktor.http.*

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

import kotlinx.coroutines.runBlocking


// models
import _4Tipsy.TinyCloudCLI.core.ReqClient
import _4Tipsy.TinyCloudCLI.core.getCookiesAsHeader
import _4Tipsy.TinyCloudCLI.models.FsEntity
import _4Tipsy.TinyCloudCLI.ui.ErrorUI
import _4Tipsy.TinyCloudCLI.ui.LsTable
import _4Tipsy.TinyCloudCLI.util.validatePathOption
import _4Tipsy.TinyCloudCLI.util.ContentSorter
import _4Tipsy.TinyCloudCLI.Config




class Ls : CliktCommand(help = "Show contents of directory") {
  val path by option("-p", "--path", help = "Specify path to directory").default("drive:/")
  val json by option("--json", help = "Prints result as raw json").flag(default = false)


  // models
  @Serializable
  data class RequestBody (
    val where: String,
  )


  // handler
  override fun run() {
    runBlocking {

      val where = validatePathOption(path)
      val client = ReqClient()
      val targetUrl = Config().main.apiRawUrl + "/fs-service/get-dir-contents"
      val requestBody = RequestBody(where)


      val response = client.post(targetUrl) {
        headers.append(HttpHeaders.Cookie, getCookiesAsHeader("/"))
        contentType(ContentType.Application.Json)
        setBody(requestBody)
      }


      // if ok
      if (response.status.isSuccess()) {

        val contentsUnsorted = response.body<List<FsEntity>>()
        val contents = ContentSorter.sort(contentsUnsorted)

        // print
        if (json) {
          val _Json = Json { prettyPrint = true }
          echo( _Json.encodeToString(contents) )

        } else {
          LsTable(where, contents)
        }
      }



      // if error
      else {
        ErrorUI(response)
      }



    }
  }


}

