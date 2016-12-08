package com.directual.service.actors.worker

import akka.actor.{Actor, ActorLogging}
import com.directual.extension.spring.SpringCtxExt
import com.directual.service.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
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


  override def receive: Receive = {
    case msg ⇒
      log.info(s"Worker ${self.path.name} " + msg)
      log.info(s"Sender " + sender().path)
      sender() ! s"Worker${self.path.name} returns ${service.addDummy(msg.toString)}"
  }
}
