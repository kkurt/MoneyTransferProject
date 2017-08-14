lazy val root = (project in file(".")).
  settings(
    name := "MoneyTransferProject",
    version := "1.0",
    scalaVersion := "2.12.2"
  )
  

libraryDependencies ++= Seq(
 "org.scala-lang" % "scala-library" % scalaVersion.value % "provided", 
  "com.typesafe.akka" %% "akka-actor" % "2.5.3",
  "com.typesafe.akka" %% "akka-http" % "10.0.8" , 
  "com.typesafe.akka" %% "akka-stream" % "2.5.3",  
  "org.slf4j" % "slf4j-nop" % "1.6.4",  
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.9"   
)


mainClass in assembly := Some("mtp.akka.RestStarter")


