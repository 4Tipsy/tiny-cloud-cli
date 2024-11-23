
package _4Tipsy.TinyCloudCLI.commands.userService


import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.mordant.rendering.TextColors.*

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.call.*
import io.ktor.http.*


import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable


// models
import _4Tipsy.TinyCloudCLI.core.ReqClient
import _4Tipsy.TinyCloudCLI.core.getCookiesAsHeader
import _4Tipsy.TinyCloudCLI.models.User
import _4Tipsy.TinyCloudCLI.Config
import _4Tipsy.TinyCloudCLI.ui.ErrorUI





class Login : CliktCommand() {
  val email by option().prompt(requireConfirmation = false)
  val password by option().prompt(requireConfirmation = false, hideInput = true)


  // models
  @Serializable
  data class RequestBody (
    val email: String,
    val password: String,
  )



  // handler
  override fun run() {
    runBlocking {

      val client = ReqClient()


      val requestBody = RequestBody(email, password)
      val targetUrl = Config().main.apiRawUrl + "/user-service/login"

      val response = client.post(targetUrl) {
        contentType(ContentType.Application.Json)
        setBody(requestBody)
      }


      // if ok
      if (response.status.isSuccess()) {


        // get user info
        val _targetUrl = Config().main.apiRawUrl + "/user-service/get-current-user"
        val user = client.get(_targetUrl) {
          headers.append(HttpHeaders.Cookie, getCookiesAsHeader("/"))
        }
          .let { it.body<User>() }


        println(green("OK [${brightWhite("200")}]: ") + "was signed as '${blue(user.name)}' <${user.isVerified}>")

      }

      // if error
      else {
        ErrorUI(response)
      }


    }
  }




}