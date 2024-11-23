
package _4Tipsy.TinyCloudCLI.util



fun prettifySize(sizeInBytes: Long): String {
  return when {
    sizeInBytes < 1_000 -> "$sizeInBytes B"
    sizeInBytes < 1_000_000 -> {
      val size = sizeInBytes / 1_000.0
      "%.2f KB".format(size)
    }
    sizeInBytes < 1_000_000_000 -> {
      val size = sizeInBytes / 1_000_000.0
      "%.2f MB".format(size)
    }
    else -> {
      val size = sizeInBytes / 1_000_000_000.0
      "%.2f GB".format(size)
    }
  }
}