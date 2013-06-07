package com.logicianslament.demo

import dispatch._
import Defaults._
import com.ning.http.client.Request
import scala.xml.Elem

object D03Option
{
  def main(args: Array[String])
  {
    val succURL = host("localhost", 9000) / "media" / "games" <<? Map("id" -> "SMB") OK as.xml.Elem
    val failURL = host("localhost", 9000) / "media" / "games" <<? Map("id" -> "I DONT EXIST") OK as.xml.Elem

    val succFuture: dispatch.Future[Option[Elem]] = Http(succURL).option
    val failFuture: dispatch.Future[Option[Elem]] = Http(failURL).option
    Thread.sleep(4000)
    println(succFuture.print)
    println(failFuture.print)
    val succXml: Option[Elem] = succFuture()
    val failXml: Option[Elem] = failFuture()
    println("Success Title: " + succXml.map(xml => (xml \ "title").text))
    println("Failure Title: " + failXml.map(xml => (xml \ "title").text))
  }
}
