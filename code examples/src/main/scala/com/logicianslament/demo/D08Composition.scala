package com.logicianslament.demo

import dispatch._
import Defaults._
import scala.Predef._

object D08Composition
{
  def main(args: Array[String])
  {
    val smbFuture = loadGame("SMB")
    val smb2Future = loadGame("SMB2")
    val smb3Future = loadGame("SMB3")
    val smwFuture = loadGame("SMW")
    val eveFuture = loadGame("EVE")
    val ds1Future = loadGame("DS1")
    val ds2Future = loadGame("DS2")

    val forCompResult = for
    {
      smb <- smbFuture.right
      smb2 <- smb2Future.right
      smb3 <- smb3Future.right
      smw <- smwFuture.right
      eve <- eveFuture.right
      ds1 <- ds1Future.right
      ds2 <- ds2Future.right
    }
    yield
    {
      List(smb, smb2, smb3, smw, eve, ds1, ds2).sortWith
      {
        case ((_, year1), (_, year2)) => year1 < year2
      }.mkString("\r\n")
    }

    Thread.sleep(4000)
    println(forCompResult())
  }

  def loadGame(gameID: String) =
  {
    Http(host("localhost", 9000) / "media" / "games" <<? Map("id" -> gameID) OK as.xml.Elem).either.
      map(_.right.map(xml => ((xml \ "title").text, (xml \ "release").text.trim.toInt)))
  }
}
