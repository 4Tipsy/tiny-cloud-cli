
package _4Tipsy.TinyCloudCLI.commands.shareService


import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.default

import com.github.ajalt.mordant.rendering.TextColors.*

import io.ktor.http.*
import io.ktor.client.call.*
import io.ktor.client.request.post
import io.ktor.client.request.get
import io.ktor.client.request.setBody

import kotlinx.serialization.Serializable
import kotlinx.coroutines.runBlocking


// modules
import _4Tipsy.TinyCloudCLI.core.ReqClient
import _4Tipsy.TinyCloudCLI.core.getCookiesAsHeader
import _4Tipsy.TinyCloudCLI.models.User
import _4Tipsy.TinyCloudCLI.ui.ErrorUI
import _4Tipsy.TinyCloudCLI.util.validatePathOption
import _4Tipsy.TinyCloudCLI.Config




class Share : CliktCommand() {
  val name by argument("<name>")

  val path by option("-p", "--path", help = "Specify path to parent dir").default("drive:/")



  // models
  @Serializable
  data class RequestBody (
    val target: String,
  )
  @Serializable
  data class ResponseBody (
    val sharedLink: String,
  )


  // handler
  override fun run() {
    runBlocking {

      val where = validatePathOption(path)
      val client = ReqClient()
      val targetUrl = Config().main.apiRawUrl + "/share-service/make-shared"
      val requestBody = RequestBody(
        target = "$where/$name",
      )


      val response = client.post(targetUrl) {
        headers.append(HttpHeaders.Cookie, getCookiesAsHeader("/"))
        contentType(ContentType.Application.Json)
        setBody(requestBody)
      }



      // if ok
      if (response.status.isSuccess()) {
        val body = response.body<ResponseBody>()


        // subrequest
        val _targetUrl = Config().main.apiRawUrl + "/user-service/get-current-user"
        val _response = client.get(_targetUrl) {
          headers.append(HttpHeaders.Cookie, getCookiesAsHeader("/"))
        }
        val user = _response.body<User>()


        println( green("Ok [${brightWhite("200")}]: ") + "made shared '$name' (at '$where')" )
        println()
        println("${Config().main.apiRawUrl}/share/${user.name}/${blue(body.sharedLink)}".quote())
      }



      // if error
      else {
        ErrorUI(response)
      }


    }
  }


}