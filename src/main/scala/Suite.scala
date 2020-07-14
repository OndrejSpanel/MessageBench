trait Suite {

  type State
  def create: State
  def stop(state: State): Unit
  def oneMessage(state: State): Unit
  def name: String
}
