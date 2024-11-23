
package _4Tipsy.TinyCloudCLI.core


import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json


// modules
import _4Tipsy.TinyCloudCLI.Config
import _4Tipsy.TinyCloudCLI.core.CookieHandler



object ReqClient {


  // configure client
  private var client = HttpClient(Apache) {

    install(UserAgent) {
      agent = "TinyCloud CLI/v2.0.0 (Ktor Client)"
    }

    install(HttpTimeout) {
      connectTimeoutMillis = Config().main.requestTimeout.let { it*1000L } // secs * 1000millis
    }

    install(ContentNegotiation) {
      json()
    }

    install(CookieHandler)

  }



  // get configured client as singleton
  operator fun invoke(): HttpClient { return this.client }

}