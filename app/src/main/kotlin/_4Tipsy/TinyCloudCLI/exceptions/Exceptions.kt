
package _4Tipsy.TinyCloudCLI.exceptions


import com.github.ajalt.clikt.core.PrintMessage




class InvalidConfigException: PrintMessage {
  constructor(message: String) : super(message)
}



