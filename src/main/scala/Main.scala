object Main extends App {

  def runSingleWorkload(suite: Suite)(state: suite.State): Unit = {
    val count = 100000
    for (i <- 0 until count) suite.oneMessage(state)
  }

  def runSuite(suite: Suite): Unit = {
    val state = suite.create
    for (i <- 0 until 10) runSingleWorkload(suite)(state)
    val start = System.currentTimeMillis()
    for (i <- 0 until 100) runSingleWorkload(suite)(state)
    val end = System.currentTimeMillis()
    println(s"${suite.name}: duration ${end - start}")
    suite.stop(state)
  }

  runSuite(FunctionCall)
  runSuite(ActorImplementation)
  runSuite(BlockingQueueImplementation)
}
