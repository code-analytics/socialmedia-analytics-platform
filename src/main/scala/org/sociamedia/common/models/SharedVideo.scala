package org.sociamedia.common.models

case class SharedVideo(
                        videoId: Int,
                        publisherId: Int,
                        videoDate: Long,
                        videoDuration: Int
                      )
