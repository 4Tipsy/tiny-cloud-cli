
package _4Tipsy.TinyCloudCLI.models

import kotlinx.serialization.Serializable


@Serializable
data class CredentialToken (
  val value: String,
  val expires: String,
)