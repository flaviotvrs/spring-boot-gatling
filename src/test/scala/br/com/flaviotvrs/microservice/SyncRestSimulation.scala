package br.com.flaviotvrs.microservice

import io.gatling.core.Predef._     // required for Gatling core structure DSL
import io.gatling.http.Predef._     // required for Gatling HTTP DSL

import scala.concurrent.duration._

class SyncRestSimulation extends Simulation {

  val rampUpTimeSecs = 5
  val testTimeSecs   = 300
  val noOfUsers      = 3000
  val minWaitMs      = 100 milliseconds
  val maxWaitMs      = 300 milliseconds

  val baseURL		= "http://localhost:8080"
  val baseName		= "Sync Rest"
  val requestName	= baseName + " - request"
  val scenarioName	= baseName + " - scenario"
  val basicURI		= "/api/service/sync"
  val paramsRequest	= "?minMs=1&maxMs=500&successRatio=0.9"

  val httpConf = http.baseURL(baseURL)

  val scn = scenario(scenarioName + " - REST")
    .during(testTimeSecs) {
      exec(
        http(requestName)
          .get(basicURI + paramsRequest)
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