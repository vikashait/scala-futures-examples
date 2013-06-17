package com.markusjais.scala.futures.examples.basics

import com.markusjais.scala.futures.examples.business.Book
import com.markusjais.scala.futures.examples.business.orders
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Success
import scala.util.Failure
import scala.concurrent.Await
import com.markusjais.scala.futures.examples.business.Fish

object BrokenConcurrencyFixed extends App {

  val fOne = Future { Thread.sleep(1000); println("doing with F1"); Thread.sleep(1000); println("done with F1"); "Tiger" }
  val fTwo = Future { Thread.sleep(1000); println("doing with F2"); Thread.sleep(1000); println("done with F2"); "Leopard" }

  val resultF = for {
    catOne <- fOne
    catTwo <- fTwo
  } yield ("cats: " + catOne + ", " + catTwo)

  resultF onComplete {
    case Success(result) => println("success: " + result)
    case Failure(t: Throwable) => println(s"Shit, something went wrong: $t")
  }

  // necessary in this dummy app because otherwise it would terminate before Future can complete
  Thread.sleep(5000)

}

