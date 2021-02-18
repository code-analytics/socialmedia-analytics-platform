package org.sociamedia.producer

import org.apache.avro.Schema
import org.apache.avro.generic.GenericData
import org.sociamedia.common.models.User

object AvroRecordGenerator {

  def makeUserRecord[T](data: T, avroSchema: Schema): GenericData.Record = {
    val avroRecord = new GenericData.Record(avroSchema)
    val user = data.asInstanceOf[User]
    avroRecord.put("firstname", user.firstname)
    avroRecord.put("lastname", user.lastname)
    avroRecord
  }

}