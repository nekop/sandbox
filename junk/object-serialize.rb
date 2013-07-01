java_import "java.io.ByteArrayInputStream"
java_import "java.io.ByteArrayOutputStream"
java_import "java.io.ObjectInputStream"
java_import "java.io.ObjectOutputStream"

def serialize(o)
  baos = ByteArrayOutputStream.new
  oos = ObjectOutputStream.new(baos)
  oos.writeObject(o)
  oos.flush()
  result = baos.toByteArray()
  oos.close
  result
end

def deserialize(bytes)
  ois = ObjectInputStream.new(ByteArrayInputStream.new(bytes))
  o = ois.readObject()
  ois.close
  o
end
