package common

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import scala.io.StdIn

/**
 * User: Apres Virabyan
 * Company: DataArt
 * Date: 5/21/2022
 * Time: 7:36 PM
 */
object HttpServerRoutingMinimal {
   def main(args: Array[String]) = {
     implicit val system = ActorSystem(Behaviors.empty, "my-system")

     implicit val executionContext = system.executionContext

     val route =
       path("weather") {
         get {
           complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Weather AKKA HTTP example</h1>"))
         }
       }


     val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

     println(s"Server now online. Please navigate to http://localhost:8080/weather\nPress RETURN to stop...")
     StdIn.readLine()
     bindingFuture
       .flatMap(_.unbind())
       .onComplete(_ => system.terminate())
   }
}
