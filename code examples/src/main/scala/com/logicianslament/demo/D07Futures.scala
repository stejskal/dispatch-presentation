package com.logicianslament.demo

import dispatch._
import Defaults._

object D07Futures
{
  def main(args: Array[String])
  {
    val url = host("localhost", 9000) / "media" / "games" <<? Map("id" -> "SMB") OK as.xml.Elem
    val future = Http(url).either

    future.onSuccess
    {
      case Left(ex) => println("exception from onSuccess: " + ex)
      case Right(xml) => println("title from onSuccess: " + (xml \ "title").text)
    }

    for (res <- future)
    yield
    {
      res match
      {
        case Left(ex) => println("exception from for comprehension: " + ex)
        case Right(xml) => println("title from for comprehension: " + (xml \ "title").text)
      }
    }

    val forCompResult = for (xml <- future.right)
    yield
    {
      val title = (xml \ "title").text
      println("title from right only for comprehension: " + title)
      title
    }

    Thread.sleep(4000)
    println(forCompResult())
  }
}
