
package _4Tipsy.TinyCloudCLI.commands.userService


import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.flag

import io.ktor.client.request.get
import io.ktor.client.call.*
import io.ktor.http.*


import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString


// modules
import _4Tipsy.TinyCloudCLI.ui.ErrorUI
import _4Tipsy.TinyCloudCLI.ui.WhoUI
import _4Tipsy.TinyCloudCLI.core.ReqClient
import _4Tipsy.TinyCloudCLI.core.getCookiesAsHeader
import _4Tipsy.TinyCloudCLI.models.User
import _4Tipsy.TinyCloudCLI.Config






class Who : CliktCommand(help = "Get current user") {

  val json by option("--json", help = "prints result as raw json").flag(default = false)


  // handler
  override fun run() {
    runBlocking {

      val client = ReqClient()
      val targetUrl = Config().main.apiRawUrl + "/user-service/get-current-user"

      val response = client.get(targetUrl) {
        headers.append(HttpHeaders.Cookie, getCookiesAsHeader("/"))
      }


      // if ok
      if (response.status.isSuccess()) {
        val user = response.body<User>()

        // print
        if (json) {
          val _Json = Json { prettyPrint = true }
          echo( _Json.encodeToString(user) )

        } else {
          WhoUI(user)
        }

      }




      // if error
      else {
        ErrorUI(response)
      }



    }
  }



}










