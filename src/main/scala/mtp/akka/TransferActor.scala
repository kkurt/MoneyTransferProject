package mtp.akka

import akka.actor.Actor
import akka.actor.Props
import Db._
import scala.collection.JavaConversions._

//Transfer AKKA Actor
class TransferActor() extends Actor
{
  
  object TransferActor {
    def props(): Props = Props(new TransferActor())
  }    

   
  var tranId=0;
  
  def receive = {
    case (sourceAccountId:Int,targetAccountId:Int,amount:Double) =>
      try {
      val userS=Accounts(sourceAccountId)
      val userT=Accounts(targetAccountId)
      userS.Balance += amount;
      userT.Balance-=amount;
      tranId+=1      
      sender ! "1"
      }
      catch {
        case t: Throwable => sender ! "0"
      }
            
      
  }
  
 
}