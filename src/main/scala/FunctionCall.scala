
object FunctionCall extends Suite {
  class State(@volatile var count: Long = 0) {
    def call(): Unit = count += 1
  }

  def create: State = new State()
  def stop(state: State): Unit = {}

  def oneMessage(state: State): Unit = state.call()

  def name: String = "FunctionCall"
}
