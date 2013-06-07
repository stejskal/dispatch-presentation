package entities

class Game(val id:String, val title: String, val releaseYear: Int, val genre: String, val difficulty: Int, val rating: Int, val platforms: List[String])
{
  lazy val xml = <game>
              <id>{id}</id>
              <title>{title}</title>
              <release>
                <year>{releaseYear}</year>
              </release>
              <genre>{genre}</genre>
              <difficulty>{difficulty}</difficulty>
              <rating>{rating}</rating>
              <platforms>
                {platforms.map(p => <platform>{p}</platform>)}
              </platforms>
            </game>
}
