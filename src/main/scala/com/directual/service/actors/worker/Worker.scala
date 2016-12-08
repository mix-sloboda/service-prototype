package com.directual.service.actors.worker

import akka.actor.{Actor, ActorLogging}
import com.directual.extension.spring.SpringCtxExt
import com.directual.service.Service
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
  * Created on 07/12/2016.
  */
@Component("worker")
@Scope("prototype")
class Worker extends Actor with ActorLogging {

  @transient var service:Service = _

  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    super.preStart()
    service = SpringCtxExt(context.system).springContext.getBean(classOf[Service])
  }

  val logMsg = s"Worker(${self.path}) {}"

  override def receive: Receive = {
    case msg ⇒
      log.info(logMsg, msg)
      log.info(s"Sender " + sender().path)
      sender() ! logMsg + s" returns ${service.addDummy(msg.toString)}"
  }
}
