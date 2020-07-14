import java.util.concurrent.LinkedBlockingQueue

object BlockingQueueImplementation extends Suite {

  trait Message
  case class Increment() extends Message
  case object Terminate extends Message

  private val inbox = new LinkedBlockingQueue[Message]()
  @volatile private var counter = 0
  private val runner: Thread = new Thread("Runner") {
    override final def run(): Unit = {
      val message = inbox.take()
      if (message != Terminate) {
        receive(message)
        run()
      }

    }
  }
  runner.start()

  def receive(message: Message) = {
    counter += 1
  }

  type State = BlockingQueueImplementation.type

  def state = this

  def create = this
  def stop(state: State) = {
    inbox.put(Terminate)
  }

  def oneMessage(state: State): Unit = {
    inbox.put(Increment())
  }

  def name = "BlockingQueueImplementation"
}

