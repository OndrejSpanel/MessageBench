import akka.actor._

import language.postfixOps

object ActorImplementation extends Suite {

  private case object Ping

  class Ponger extends Actor {
    @volatile var count = 0

    def receive = {
      case Ping =>
        count += 1
    }
  }

  private val system = ActorSystem("ActorSystem")

  private val ponger = system.actorOf(Props(classOf[Ponger]), "ponger")

  def call(): Unit = {
    ponger ! Ping
  }

  type State = this.type

  def create: ActorImplementation.type = this
  def stop(state: State): Unit = system.terminate()

  def oneMessage(state: ActorImplementation.type): Unit = state.call()

  def name: String = "ActorImplementation"
}
