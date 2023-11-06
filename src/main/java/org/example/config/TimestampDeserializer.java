package org.example.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampDeserializer extends JsonDeserializer<Timestamp> {
  @Override
  public Timestamp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    String dateStr = jsonParser.getText();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      Date date = dateFormat.parse(dateStr);
      return new Timestamp(date.getTime());
    } catch (Exception e) {
      throw new IOException("Error parsing Timestamp", e);
    }
  }
}
