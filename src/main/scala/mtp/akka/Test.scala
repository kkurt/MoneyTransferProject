package mtp.akka


import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import akka.compat.Future
import scala.concurrent.Future
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.HttpRequest
import akka.util.Timeout
import scala.concurrent.duration._
import java.net.{URL, HttpURLConnection}


object Test {

  
  def callRest(s:String,callType:String):String= {
    var connection = (new URL(s)).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(5000)
    connection.setReadTimeout(5000)
    connection.setRequestMethod(callType)
    var inputStream = connection.getInputStream
    var content = io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close
    return content.asInstanceOf[String]
  }
  def getRest(s:String):String= {
    return callRest(s,"GET")
  }
  def postRest(s:String):String= {
    return callRest(s,"POST")
  }
  
  
  def getLastBalances() {
    //Account Balance Request
    var balance=getRest("http://localhost:9090/accountBalance/1")
    println(s"User1 balance: $balance")
    
    balance=getRest("http://localhost:9090/accountBalance/2")
    println(s"User2 balance: $balance")
    
    balance=getRest("http://localhost:9090/accountBalance/3")
    println(s"User3 balance: $balance\n")
  }
  
  def main(args: Array[String]): Unit = {

    println("Starting :")     
    getLastBalances;
        
    //Transferring 50 from User1 to User2 
    postRest("http://localhost:9090/transfer/1/2/50")
    println(s"Transferring 50 from User1 to User2")
    println("After transfer :")
    getLastBalances;
    
    //Transferring 100 from User3 to User1 
    postRest("http://localhost:9090/transfer/3/1/50")
    println(s"Transferring 100 from User3 to User1")
    println("After transfer :")
    getLastBalances;

    //Transferring 5 from User2 to User3 
    postRest("http://localhost:9090/transfer/2/3/5")
    println(s"Transferring 5 from User2 to User3")
    println("After transfer :")
    getLastBalances;


  }

}