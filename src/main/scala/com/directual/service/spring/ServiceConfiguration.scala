package com.directual.service.spring

import com.directual.extension.spring.config.ActorSystemConfigurationInjector
import com.typesafe.config.{Config, ConfigFactory}
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}

/**
  * Created on 06/12/2016.
  */
@Configuration
@ComponentScan(
  Array(
    "com.directual.service.actors",
    "com.directual.service"
  )
)
class ServiceConfiguration extends ActorSystemConfigurationInjector {

}
