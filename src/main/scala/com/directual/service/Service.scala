package com.directual.service

import org.springframework.stereotype.{Component, Service}

/**
  * Created on 07/12/2016.
  */

@Component
class Service {
  def addDummy(str:String) = str + " << Dummy!"
}