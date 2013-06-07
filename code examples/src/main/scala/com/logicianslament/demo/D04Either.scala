package com.logicianslament.demo

import dispatch._
import Defaults._
import com.ning.http.client.Request
import scala.xml.Elem

object D04Either
{
  def main(args: Array[String])
  {
    val succURL = host("localhost", 9000) / "media" / "games" <<? Map("id" -> "SMB")
    val failURL = host("localhost", 9000) / "media" / "games" <<? Map("id" -> "I DONT EXIST")
    val succUrlAndOkHandler: (Request, OkFunctionHandler[Elem]) = succURL OK as.xml.Elem
    val failUrlAndOkHandler: (Request, OkFunctionHandler[Elem]) = failURL OK as.xml.Elem

    val succFuture: dispatch.Future[Either[Throwable, Elem]] = Http(succUrlAndOkHandler).either
    val failFuture: dispatch.Future[Either[Throwable, Elem]] = Http(failUrlAndOkHandler).either
    Thread.sleep(4000)
    println(succFuture.print)
    println(failFuture.print)
    val succXml: Either[Throwable, Elem] = succFuture()
    val failXml: Either[Throwable, Elem] = failFuture()
    println(succXml.right.map(xml => (xml \ "title").text))
    println(failXml.right.map(xml => (xml \ "title").text))
  }
}
