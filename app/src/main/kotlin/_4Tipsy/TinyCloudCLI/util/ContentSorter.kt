
package _4Tipsy.TinyCloudCLI.util


import _4Tipsy.TinyCloudCLI.models.FsEntity
import _4Tipsy.TinyCloudCLI.models.FsEntityWithPath




object ContentSorter {


  @JvmName("_1")
  fun sort(contents: List<FsEntity>): List<FsEntity> {
    return contents.sortedBy { it.baseType == _4Tipsy.TinyCloudCLI.models.BaseType.File }
  }

  @JvmName("_2")
  fun sort(contents: List<FsEntityWithPath>): List<FsEntityWithPath> {
    return contents.sortedBy { it.baseType == _4Tipsy.TinyCloudCLI.models.BaseType.File }
  }


}



