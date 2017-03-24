package pulse.services.example.extensions

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

trait Managed[A] {
  def close(instance: A): Unit
}

object Managed {
  implicit object ManagedByteArrayOutputStream extends Managed[ByteArrayOutputStream] {
    override def close(instance: ByteArrayOutputStream): Unit = instance.close()
  }
  implicit object ManagedByteArrayInputStream extends Managed[ByteArrayInputStream] {
    override def close(instance: ByteArrayInputStream): Unit = instance.close()
  }
}