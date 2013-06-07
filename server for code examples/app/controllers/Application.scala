package controllers

import play.api.mvc._
import entities.Game
import java.io.{FileWriter, BufferedWriter, File}
import java.lang.String
import scala.Predef.String
import scala.io.Source

object Application extends Controller
{
  val games = List(new Game("SMB", "Super Mario Bros.", 1985, "Platforming", 5, 10, List("NES")),
    new Game("EVE", "Eve Online", 2003, "Space Sim MMORPG", 7, 8, List("PC")),
    new Game("DS1", "Demon's Souls", 2009, "Action Role-Playing", 9, 9, List("PS3")),
    new Game("DS2", "Dark Souls", 2011, "Action Role-Playing", 10, 9, List("PS3", "XBox 360", "PC")),
    new Game("SMB2", "Super Mario Bros. 2", 1988, "Platforming", 4, 7, List("NES")),
    new Game("SMB3", "Super Mario Bros. 3", 1988, "Platforming", 4, 10, List("NES")),
    new Game("SMW", "Super Mario World", 1990, "Platforming", 4, 10, List("SNES")))

  def index = Action
  {
    Ok(games(3).xml)
  }

  def game(id: String, delay: Int) =
  {
    Thread.sleep(delay * 1000)
    val gameOp = games.find(_.id == id)
    Action
    {
      gameOp match
      {
        case None => NotFound
        case Some(game) => Ok(game.xml)
      }
    }

  }

  def file =
  {
    def createTempFile =
    {
      val file = File.createTempFile("example", "xml")
      var writer: BufferedWriter = null
      try
      {
        writer = new BufferedWriter(new FileWriter(file))
        writer.write("pointless example file\r\nsecond line of file\r\nlast line")
      }
      finally
      {
        if (writer != null)
        {
          writer.close()
        }
      }
      file
    }
    Action
    {
      Ok.sendFile(createTempFile, false, f => "example.txt")
    }
  }

  def upload = Action(parse.temporaryFile) { request =>
    Ok("Your file content was:\r\n" + Source.fromFile(request.body.file).getLines().mkString("\r\n"))
  }
}