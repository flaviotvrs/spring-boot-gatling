package br.com.itau.comarhe.microservice

import io.gatling.core.Predef._     // required for Gatling core structure DSL
import io.gatling.http.Predef._     // required for Gatling HTTP DSL

import scala.concurrent.duration._

class AllGestoresRestSimulation extends Simulation {

  val rampUpTimeSecs = 5
  val testTimeSecs   = 300
  val noOfUsers      = 3000
  val minWaitMs      = 100 milliseconds
  val maxWaitMs      = 300 milliseconds

  val baseURL		= "http://localhost:8080"
  val baseName		= "EU7 repository API"
  val requestName	= baseName + " - request"
  val scenarioName	= baseName + " - scenario"
  val basicURI		= "/gestor"
  val paramsGestor	= "?incluirFundos=false"
  val paramsGestorFundos	= "?incluirFundos=true"

  val httpConf = http.baseURL(baseURL)

  val scn = scenario(scenarioName + " - REST")
    .during(testTimeSecs) {
      exec(
        http(requestName + " - all gestores")
          .get(basicURI + paramsGestor)
          .check(status.is(200))
      )
      .pause(minWaitMs, maxWaitMs)
      .exec(
        http(requestName + " - all gestores all fundos")
          .get(basicURI + paramsGestorFundos)
          .check(status.is(200))
      )
      .pause(minWaitMs, maxWaitMs)
    }

  setUp(scn.inject(
  	atOnceUsers(30),
  	nothingFor(30 seconds),
  	constantUsersPerSec(7) during (30 seconds),
  	nothingFor(30 seconds),
  	constantUsersPerSec(7) during (200 seconds)
  ).protocols(httpConf))
}