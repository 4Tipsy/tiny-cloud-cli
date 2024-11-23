
package _4Tipsy.TinyCloudCLI.commands.fsService


import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.default

import com.github.ajalt.mordant.rendering.TextColors.*

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.*

import kotlinx.serialization.Serializable
import kotlinx.coroutines.runBlocking


// modules
import _4Tipsy.TinyCloudCLI.core.ReqClient
import _4Tipsy.TinyCloudCLI.core.getCookiesAsHeader
import _4Tipsy.TinyCloudCLI.ui.ErrorUI
import _4Tipsy.TinyCloudCLI.util.validatePathOption
import _4Tipsy.TinyCloudCLI.Config



class Mkdir : CliktCommand(help = "Create new directory") {
  val name by argument("<name>")

  val path by option("-p", "--path", help = "Specify path to parent dir").default("drive:/")



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
      val targetUrl = Config().main.apiRawUrl + "/fs-service/create-new-dir"
      val requestBody = RequestBody(
        where = where,
        name = name,
      )


      val response = client.post(targetUrl) {
        headers.append(HttpHeaders.Cookie, getCookiesAsHeader("/"))
        contentType(ContentType.Application.Json)
        setBody(requestBody)
      }



      // if ok
      if (response.status.isSuccess()) {

        println( green("Ok [${brightWhite("200")}]: ") + "created '$name'<Dir> (at '$where')" )
      }



      // if error
      else {
        ErrorUI(response)
      }


    }
  }


}