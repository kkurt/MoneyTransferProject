package mtp.akka

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.Directives._enhanceRouteWithConcatenation
import akka.http.scaladsl.server.Directives._segmentStringToPathMatcher
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.Directives.get
import akka.http.scaladsl.server.Directives.parameters
import akka.http.scaladsl.server.Directives.path
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import Db._
import akka.http.scaladsl.server.Directives._
import akka.event.Logging
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.Future
import akka.pattern.ask
import akka.util.Timeout
import akka.http.scaladsl.model.HttpResponse


//Akka REST Starter
object Starter {

  val SYSTEM_NAME = "MoneyTransferSystem"
  val AKKA_REST_PORT = 9090

  implicit val system: ActorSystem = ActorSystem(SYSTEM_NAME)
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val transferActor: ActorRef = system.actorOf(Props(new TransferActor()), SYSTEM_NAME)

  var tranId = 0

  val route1 = path("transfer" / IntNumber / IntNumber / DoubleNumber) { (sourceAccountId, targetAccountId, amount) =>
      post {
        tranId += 1
        implicit val timeout: Timeout = Timeout(5 seconds)
        val future: Future[String] = ask(transferActor, (sourceAccountId, targetAccountId, amount)).mapTo[String]
        val result = Await.result(future, 5 seconds).asInstanceOf[String]
        printAccounts();
        complete {
          result
        }

      }
    }
  
  val route2 = path("accountBalance" / IntNumber) { accountId =>
      get {        
        complete {
          Db.Accounts(accountId).Balance.toString()
        }

      }
    }

    val routes = route1~route2
  
  def main(args: Array[String]): Unit = {

    
    var bindingFuture2 = Http().bindAndHandle(routes, "0.0.0.0", AKKA_REST_PORT)
    println("Akka http REST Server is ready!\r")

    //system.scheduler.schedule(0 seconds, 10 seconds,classifier,1)

  }
    

    
}