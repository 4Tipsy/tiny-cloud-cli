
package _4Tipsy.TinyCloudCLI.core

import com.github.ajalt.mordant.rendering.TextColors.*

import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.io.File

// modules
import _4Tipsy.TinyCloudCLI.core.CacheHandler
import _4Tipsy.TinyCloudCLI.util.getTermSize



fun getDirArchived(targetDir: File): File {
  val _w = getTermSize().first

  val arcFile = File.createTempFile( "arc_", ".tmp", File(CacheHandler.CACHE_DIR) )


  ZipOutputStream(BufferedOutputStream(FileOutputStream(arcFile))).use { zipStream ->
    targetDir.walkTopDown().forEach { file ->
      val _fileName = file.absolutePath.removePrefix(targetDir.absolutePath).removePrefix("/")
      val entry = ZipEntry( "$_fileName${(if (file.isDirectory) "/" else "" )}")
      zipStream.putNextEntry(entry)
      if (file.isFile) {
        print(" ".repeat(_w) + "\r")
        print(gray("packing '${file.name}'\r"))
        file.inputStream().use { it.copyTo(zipStream) }
      }
    }
    print(" ".repeat(_w) + "\r")
  }
  return arcFile
}