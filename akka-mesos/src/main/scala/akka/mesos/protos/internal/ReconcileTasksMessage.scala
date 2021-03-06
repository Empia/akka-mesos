package akka.mesos.protos.internal

import akka.mesos.protos.{ ProtoReads, ProtoWrapper, TaskStatus, FrameworkID }
import mesos.internal.Messages

import scala.collection.JavaConverters._
import scala.util.Try

final case class ReconcileTasksMessage(
    frameworkId: FrameworkID,
    statuses: Seq[TaskStatus]) extends ProtoWrapper[Messages.ReconcileTasksMessage] {
  def toProto: Messages.ReconcileTasksMessage =
    Messages.ReconcileTasksMessage
      .newBuilder
      .setFrameworkId(frameworkId.toProto)
      .addAllStatuses(statuses.map(_.toProto).asJava)
      .build()
}

object ReconcileTasksMessage extends ProtoReads[ReconcileTasksMessage] {
  def fromBytes(bytes: Array[Byte]): Try[ReconcileTasksMessage] = Try {
    ReconcileTasksMessage(Messages.ReconcileTasksMessage.parseFrom(bytes))
  }

  def apply(proto: Messages.ReconcileTasksMessage): ReconcileTasksMessage =
    ReconcileTasksMessage(
      FrameworkID(proto.getFrameworkId),
      proto.getStatusesList.asScala.map(TaskStatus(_)))
}
