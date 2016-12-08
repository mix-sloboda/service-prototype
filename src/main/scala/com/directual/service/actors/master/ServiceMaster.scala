package com.directual.service.actors.master

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.cluster.routing.{ClusterRouterGroup, ClusterRouterGroupSettings, ClusterRouterPool, ClusterRouterPoolSettings}
import akka.routing.{BroadcastPool, ConsistentHashingGroup, FromConfig, RoundRobinPool}
import com.directual.extension.spring.SpringCtxExt
import com.directual.service.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
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
      log.info("Remote answer " + msg)
    case msg:String ⇒
      log.info("Receved " + msg)
      remoteRouter ! msg
  }

  def createWorkerRouter(): ActorRef = {
    context.actorOf(ClusterRouterGroup(ConsistentHashingGroup(Nil), ClusterRouterGroupSettings(
      totalInstances = 100, routeesPaths = List("/user/worker-supervisor"),
      allowLocalRoutees = false,
      useRole = Some("prototype-worker"))).props(),
      name = "worker-router"
    )
  }
}
