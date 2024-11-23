
package _4Tipsy.TinyCloudCLI.ui


import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.call.body
import io.ktor.http.contentType
import io.ktor.http.ContentType

import com.github.ajalt.mordant.rendering.TextColors.*

import kotlinx.serialization.SerializationException
import kotlinx.coroutines.runBlocking


// modules
import _4Tipsy.TinyCloudCLI.models.ErrorBasicResp





class ErrorUI (private val response: HttpResponse) {

  init {
    runBlocking {


      // if json
      if (response.contentType() == ContentType.Application.Json) {
        // if basic
        try {

          val body = response.body<ErrorBasicResp>()
          val code = brightWhite(response.status.value.toString())
          println( red("Error [$code]: ${body.errorType}") )
          println( red("Detail: ") + body.errorDetail )


        } catch (exc: SerializationException) {
          val code = brightWhite(response.status.value.toString())
          println( red("Error [$code]: Unexpected response json type") )
          println( blue(response.bodyAsText()) )
        }
      }


      // if not
      else {
        val code = brightWhite(response.status.value.toString())
        println( red("Error [$code]: Unexpected response type") )
        println( blue(response.bodyAsText()) )
      }


    }
  }




}