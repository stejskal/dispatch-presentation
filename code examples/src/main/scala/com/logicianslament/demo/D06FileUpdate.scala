package com.logicianslament.demo

import dispatch._
import Defaults._
import java.io.{FileWriter, BufferedWriter, File}

object D06FileUpdate
{
  def main(args: Array[String])
  {
    val file = createTempFile
    val url = host("localhost", 9000) / "media" / "file"
    val urlAndOkHandler = url.POST.setBody(file)
    val future = Http(urlAndOkHandler OK as.String)
    Thread.sleep(4000)
    println(future())
  }

  def createTempFile =
  {
    val file = File.createTempFile("example", "txt")
    val writer = new BufferedWriter(new FileWriter(file))
    writer.write("video games rule\r\ndispatch rules too")
    writer.close()
    file
  }
}
