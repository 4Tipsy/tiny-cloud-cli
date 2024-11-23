
package _4Tipsy.TinyCloudCLI.models


import kotlinx.serialization.Serializable



@Serializable
data class ErrorBasicResp (
  val errorType: String,
  val errorDetail: String,
)