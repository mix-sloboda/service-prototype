package com.directual.service

import akka.actor.ActorSystem
import akka.cluster.Cluster
import com.directual.extension.spring.SpringCtxExt
import org.springframework.context.annotation.AnnotationConfigApplicationContext

import scala.concurrent.Await
import concurrent.duration._
import com.typesafe.config.ConfigFactory

object Main extends App {

  val config = ConfigFactory.load()

  implicit val system = ActorSystem("directual", config)

  import system.dispatcher

  if(system.settings.config.getString("directual.service.role") == "master") {
      system.log.info("PROTOTYPE MASTER")

      SpringCtxExt(system).actorOf("worker-supervisor")

      val serviceMaster = SpringCtxExt(system).actorOf("serviceMaster")

      system.log.info("Master node is ready.")

      system.scheduler.schedule(10.seconds, 5.seconds, serviceMaster, "Bob")
  } else {
    SpringCtxExt(system).actorOf("worker-supervisor")
  }

  sys.addShutdownHook({
    val cluster = Cluster(system)
    cluster.leave(cluster.selfAddress)
    Await.ready(system.terminate(), 5.seconds)
  })
}

