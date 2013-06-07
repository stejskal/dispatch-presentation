package com.logicianslament.demo

import dispatch._
import Defaults._
import java.io.File
import scala.io.Source

object D05OkFileHandler
{
  def main(args: Array[String])
  {
    val url = host("localhost", 9000) / "media" / "file"
    val file = File.createTempFile("example", "txt")
    val urlAndOkHandler = url > as.File(file)
    Http(urlAndOkHandler)
    Thread.sleep(4000)
    println(Source.fromFile(file).getLines().mkString("\r\n"))
  }
}
