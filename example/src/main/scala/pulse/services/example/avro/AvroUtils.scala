package pulse.services.example.avro

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, DataInputStream, File}

import org.apache.avro.Schema
import org.apache.avro.file.DataFileWriter
import org.apache.avro.generic.{GenericDatumReader, GenericDatumWriter, GenericRecord}
import org.apache.avro.io.DecoderFactory
import pulse.services.example.extensions._

case object AvroUtils {

  def jsonToAvroBytes(json: String, schema:String) = {
    val schemaSpec = loadSchema(schema)
    bracket(new ByteArrayOutputStream())(output => {
      bracket(new ByteArrayInputStream(json.getBytes))(input => {
        val writer = new DataFileWriter[GenericRecord](new GenericDatumWriter[GenericRecord]())
        writer.create(schemaSpec, output)
        val reader = new GenericDatumReader[GenericRecord](schemaSpec)
        val datum = reader.read(null, getJsonDecoder(input, schemaSpec))
        writer.append(datum)
        writer.flush()
      })
      output.toByteArray
    })
  }

  def getJsonDecoder(input: ByteArrayInputStream, schema: Schema) =
    DecoderFactory.get.jsonDecoder(schema, new DataInputStream(input))

  def loadSchema(schema:String) =
      new Schema.Parser().parse(new File(schema))
}