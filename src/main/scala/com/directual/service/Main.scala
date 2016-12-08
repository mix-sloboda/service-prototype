package com.directual.service

import akka.actor.ActorSystem
import akka.cluster.Cluster
import com.directual.extension.spring.{SpringCtxExt}
import org.springframework.context.annotation.AnnotationConfigApplicationContext

import scala.concurrent.Await
import concurrent.duration._
import akka.pattern._
import com.directual.service.spring.ServiceConfiguration

import scala.util.{Failure, Success}

object Main extends App {

  val ctx = new AnnotationConfigApplicationContext(classOf[ServiceConfiguration])

  implicit val system = ctx.getBean(classOf[ActorSystem])

  import system.dispatcher

  if(system.settings.config.getStringList("akka.cluster.roles").contains("prototype-master")) {
    Cluster(system).registerOnMemberUp {
      system.log.info("PROTOTYPE MASTER")
      val serviceMaster = SpringCtxExt(system).actorOf("serviceMaster")

      system.log.info("Master node is ready.")

      system.scheduler.schedule(10.seconds, 5.seconds, serviceMaster, "Bob")
    }
  } else  {
    SpringCtxExt(system).actorOf("worker-supervisor")
  }

  sys.addShutdownHook({
    val cluster = Cluster(system)
    cluster.leave(cluster.selfAddress)
    Await.ready(system.terminate(), 5.seconds)
  })
}

