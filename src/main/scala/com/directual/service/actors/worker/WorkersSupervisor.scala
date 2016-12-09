package com.directual.service.actors.worker

import akka.actor.{Actor, ActorLogging, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}
import com.directual.extension.spring.SpringCtxExt
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
  * Created on 07/12/2016.
  */
@Component("worker-supervisor")
@Scope("prototype")
class WorkersSupervisor extends Actor with ActorLogging {

  import context._

  val workerProps = SpringCtxExt(system).props("worker")

  var workers = {
    Router(RoundRobinRoutingLogic(), Vector.fill(5) {
      ActorRefRoutee(watch(actorOf(workerProps)))
    })
  }

  log.info("WorkerSupervisor CREATED " + self.path.name)

  override def receive: Receive = {
    case msg ⇒
      log.info("Received: " + msg)
      workers.route(msg, sender())
    case Terminated(ref) ⇒
      workers.removeRoutee(ref)
      workers = workers.addRoutee(watch(actorOf(workerProps)))
  }
}

