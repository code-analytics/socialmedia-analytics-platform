package org.socialmedia

import org.socialmedia.AvroConsumer.streamData


object Main extends ConsumerConfig {

  def main(args: Array[String]): Unit = {
    streamData(topicsToConsume(0))
  }

}
