package com.logicianslament.demo

import dispatch._
import Defaults._
import com.ning.http.client.Request
import scala.xml.Elem

object D02OkXmlHandler
{
  def main(args: Array[String])
  {
    val succURL = host("localhost", 9000) / "media" / "games" <<? Map("id" -> "SMB")
    val failURL = host("localhost", 9000) / "media" / "games" <<? Map("id" -> "I DONT EXIST")
    val succUrlAndOkHandler: (Request, OkFunctionHandler[Elem]) = succURL OK as.xml.Elem
    val failUrlAndOkHandler: (Request, OkFunctionHandler[Elem]) = failURL OK as.xml.Elem

    val succFuture: dispatch.Future[Elem] = Http(succUrlAndOkHandler)
    val failFuture: dispatch.Future[Elem] = Http(failUrlAndOkHandler)
    Thread.sleep(4000)
    println(succFuture.print)
    println(failFuture.print)
    val succXml: Elem = succFuture()
    println("Success Title: " + (succXml \ "title").text)
    val failXml: Elem = failFuture()
  }
}
