package mtp.akka

import akka.actor.Actor
import akka.actor.Props
import scala.collection.immutable.HashMap


//Db object for this project
object Db {

  class Account( var Id: Int, var Name: String, var Balance: Double)
  class Transfer(var TranId: Int, var SourceId: Int, var TargetId: Int, var Amount: Double)

  var Accounts=Map(1 -> new Account(1,"User1",100),
                   2 -> new Account(2,"User2",1000),
                   3 -> new Account(3,"User3",10000)
                  )
                  
  def printAccounts() {
	  println("________________Accounts Info______________")
    for((k,v)<-Accounts) {
      println(s"Id:${v.Id},Name:${v.Name},Balance:${v.Balance}")
    }
	  println("___________________________________________")
  }

}