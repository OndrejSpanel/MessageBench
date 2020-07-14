import java.util.concurrent.{ConcurrentLinkedQueue, LinkedBlockingQueue, Semaphore}

object ConcurrentQueueImplementation extends Suite {

  trait Message
  case class Increment() extends Message
  case object Terminate extends Message

  private val inbox = new ConcurrentLinkedQueue[Message]()
  private val semaphore = new Semaphore(0)

  @volatile private var counter = 0
  private val runner: Thread = new Thread("Runner") {
    override final def run(): Unit = {
      semaphore.acquire()
      val message = inbox.remove()
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

  type State = ConcurrentQueueImplementation.type

  def state = this

  def create = this
  def stop(state: State) = {
    inbox.add(Terminate)
    semaphore.release()
  }

  def oneMessage(state: State): Unit = {
    inbox.add(Increment())
    semaphore.release()
  }

  def name = "ConcurrentQueueImplementation"
}

