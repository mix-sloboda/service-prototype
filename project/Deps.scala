import sbt._
import Keys._
object Deps {
  val ivyLocalRepo = Resolver.file("local", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)
  val mavenLocalRepo = "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"
  val akkaVersion = "2.4.14"
}