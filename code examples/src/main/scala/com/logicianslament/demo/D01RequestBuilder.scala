package com.logicianslament.demo

import dispatch._
import Defaults._
import com.ning.http.client.{RequestBuilder, Response}
import java.net.{HttpURLConnection, URL}
import java.io.InputStream
import scala.io.Source

object D01RequestBuilder
{
  def main(args: Array[String])
  {
    val hostOnlyURL: RequestBuilder = host("localhost", 9000)
    val mediaUrl: RequestBuilder = hostOnlyURL / "media"
    val gamesUrl: RequestBuilder = mediaUrl / "games"
    val withParams: RequestBuilder = gamesUrl <<? Map("id" -> "SMB")
    val succFuture: dispatch.Future[Response] = Http(withParams)
    val failFuture: dispatch.Future[Response] =
      Http(host("localhost", 9000) / "media" / "games" <<? Map("id" -> "I DONT EXIST"))
    Thread.sleep(4000)
    val succResponse: Response = succFuture()
    println("Successful Response status code: " + succResponse.getStatusCode)
    println("Successful Response content type: " + succResponse.getContentType)
    val failResponse: Response = failFuture()
    println("Failure response Status code: " + failResponse.getStatusCode)
    println("Failure Response content type: " + failResponse.getContentType)
  }

  def dispatchlessVersion
  {

    var connection: HttpURLConnection = null
    var in: InputStream = null
    var err: InputStream = null
    try
    {
      val url = new URL("http://localhost:9000/media/games?id=SMB")
      connection = url.openConnection.asInstanceOf[HttpURLConnection]
      connection.setReadTimeout(0)
      connection.addRequestProperty("accept", "application/xml")
      if (connection.getResponseCode == 200)
      {
        in = connection.getInputStream
        val output = Source.fromInputStream(in).toList.mkString("")
        println("success: " + output)
      }
      else
      {
        err = connection.getErrorStream
        println("failure: ")
        if (err != null)
        {
          val output = Source.fromInputStream(err).mkString("")
          println(output)
        }
      }
    }
    catch
      {
        case e: Exception => e.printStackTrace()
      }
    finally
    {
      if (in != null)
      {
        try
        {
          in.close()
        }
      }
      if (err != null)
      {
        try
        {
          err.close()
        }
      }
      if (connection != null)
      {
        try
        {
          connection.disconnect()
        }
      }
    }
  }
}
