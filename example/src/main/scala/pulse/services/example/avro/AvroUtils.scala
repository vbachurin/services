package pulse.services.example.avro

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, DataInputStream, File}

import org.apache.avro.Schema
import org.apache.avro.generic.{GenericData, GenericDatumReader, GenericDatumWriter, GenericRecord}
import org.apache.avro.data.Json.ObjectReader
import org.apache.avro.file.{DataFileReader, DataFileWriter, FileReader}
import org.apache.avro.generic.GenericData.Record
import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.apache.avro.specific.SpecificDatumWriter

case object AvroUtils {

  def jsonToAvroBytes(json: String, schema:String): Array[Byte] = {
    val schemaSpec = loadSchema(schema)
    val reader = new GenericDatumReader[GenericRecord](schemaSpec)
    val input = new ByteArrayInputStream(json.getBytes)
    val output = new ByteArrayOutputStream()
    val din = new DataInputStream(input)
    val writer = new DataFileWriter[GenericRecord](new GenericDatumWriter[GenericRecord]())
    writer.create(schemaSpec, output)
    val decoder = DecoderFactory.get.jsonDecoder(schemaSpec, din)
    // todo: read while not end of reader
    val datum = reader.read(null, decoder)
    writer.append(datum)
    writer.flush()
    input.close()
    val bytes = output.toByteArray
    output.close()
    bytes
  }

  def loadSchema(schema:String): Schema = {
    val parser = new Schema.Parser()
    parser.parse(new File(schema))
  }

  def toBinary(record: GenericData.Record): Array[Byte] = {
    val writer = new SpecificDatumWriter[GenericRecord](record.getSchema)
    val out = new ByteArrayOutputStream()
    val encoder = EncoderFactory.get().binaryEncoder(out, null)
    writer.write(record, encoder)
    encoder.flush()
    val binary = out.toByteArray
    out.close()
    binary
  }
}