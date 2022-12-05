package it.pps.ddos.device.actuator

import scala.annotation.targetName
import scala.collection.immutable.{HashMap, ListMap}
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import it.pps.ddos.device.DeviceProtocol._

object BasicState:
    def apply[T](name: String): BasicState[T] = new BasicState(name)

class BasicState[T](name: String) extends State[T](name):
    private val behavior: Behavior[Message] = Behaviors.receiveMessage[Message] { msg =>
        msg match
            case MessageWithReply(msg: T, replyTo, args: _*) =>
                replyTo ! Approved()
                Behaviors.same
            case Stop() => Behaviors.stopped
            case _ => Behaviors.same
    }

    override def getBehavior: Behavior[Message] = behavior

    override def copy(): State[T] = BasicState[T](name)