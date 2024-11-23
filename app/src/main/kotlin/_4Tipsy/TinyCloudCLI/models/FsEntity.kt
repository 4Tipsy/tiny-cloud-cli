
package _4Tipsy.TinyCloudCLI.models


import kotlinx.serialization.Serializable




enum class BaseType {
  File, Directory
}



@Serializable
data class FsEntity (

  val eid: String,
  val parentEid: String?, // null if in root
  val ownerUid: String,

  val name: String,
  val baseType: BaseType,

  /* if file */
  val mimeType: String?,
  val size: Long?,
  val createdAt: String?,
  val modifiedAt: String?,

  /* is shared */
  val isShared: Boolean,
  val sharedLink: String?,

)





@Serializable
data class FsEntityWithPath (

  val eid: String,
  val parentEid: String?, // null if in root
  val ownerUid: String,

  val name: String,
  val baseType: BaseType,

  /* if file */
  val mimeType: String?,
  val size: Long?,
  val createdAt: String?,
  val modifiedAt: String?,

  /* is shared */
  val isShared: Boolean,
  val sharedLink: String?,


  /* path */
  val _pseudoFsPath: String,
)