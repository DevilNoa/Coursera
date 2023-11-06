package org.example.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampSerializer extends JsonSerializer<Timestamp> {
  @Override
  public void serialize(
      Timestamp timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    jsonGenerator.writeString(dateFormat.format(new Date(timestamp.getTime())));
  }
}
