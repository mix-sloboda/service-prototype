package com.directual.service.spring

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
class ServiceConfiguration  {

}
