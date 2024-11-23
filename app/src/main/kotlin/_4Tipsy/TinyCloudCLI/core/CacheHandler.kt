
package _4Tipsy.TinyCloudCLI.core

import java.io.File
import java.nio.file.Files


// modules
import _4Tipsy.TinyCloudCLI.APP_LOC



object CacheHandler {

  val CACHE_DIR = APP_LOC + File.separator + "cache"



  fun clearCache() {
    val cacheDir = File(CACHE_DIR)
    if (!cacheDir.exists()) cacheDir.mkdir() // mkdir if not exist
    println("Clearing cache...")
    Files.walk(cacheDir.toPath())
      .sorted(Comparator.reverseOrder())
      .map { it.toFile() }
      .forEach { it.delete() }
    cacheDir.mkdir()
  }




}