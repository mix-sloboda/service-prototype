package com.directual.service.actors.master

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.cluster.routing.{ClusterRouterGroup, ClusterRouterGroupSettings, ClusterRouterPool, ClusterRouterPoolSettings}
import akka.routing._
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
  * Created on 07/12/2016.
  */
@Component("serviceMaster")
@Scope("prototype")
class ServiceMaster extends Actor with ActorLogging{

  import context._

  val config = system.settings.config.getConfig("directual")

   log.info("Created " + self.path.name)

  val remoteRouter = createWorkerRouter()

  override def receive: Receive = {
    case msg:String if msg.contains("Dummy") ⇒
      log.info("Remote answer {} from {}", msg , sender().path)
    case msg:String ⇒
      log.info("Receved Init Work " + msg)
      remoteRouter ! msg
  }

  def createWorkerRouter(): ActorRef = {
    context.actorOf(ClusterRouterGroup(RoundRobinGroup(Nil), ClusterRouterGroupSettings(
      totalInstances = 100, routeesPaths = List("/user/worker-supervisor"),
      allowLocalRoutees = true,
      useRole = Some(config.getString("service.name")))).props(),
      name = "worker-router"
    )
  }
}
