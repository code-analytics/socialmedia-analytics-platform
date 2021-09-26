package org.socialmedia

import org.socialmedia.AvroConsumer.streamData

object Consumer extends App {
  val topic = if (args.length > 0) args(0) else null
  if (topic == null) println("No argument was provided")
  else streamData(topic)
}
