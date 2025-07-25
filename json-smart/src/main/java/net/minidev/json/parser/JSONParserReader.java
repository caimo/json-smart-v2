package net.minidev.json.parser;

/*
 *    Copyright 2011-2024 JSON-SMART authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import static net.minidev.json.parser.ParseException.ERROR_UNEXPECTED_EOF;

import java.io.IOException;
import java.io.Reader;
import net.minidev.json.JSONValue;
import net.minidev.json.writer.JsonReaderI;

/**
 * Parser for JSON text. Please note that JSONParser is NOT thread-safe.
 *
 * @author Uriel Chemouni &lt;uchemouni@gmail.com&gt;
 */
class JSONParserReader extends JSONParserStream {
  private Reader in;

  // len
  public JSONParserReader(int permissiveMode) {
    super(permissiveMode);
  }

  public JSONParserReader(Reader in, int permissiveMode) {
    super(permissiveMode);
    this.in = in;
  }

  /**
   * use to return Primitive Type, or String, Or JsonObject or JsonArray generated by a
   * ContainerFactory
   */
  public Object parse(Reader in) throws ParseException {
    return parse(in, JSONValue.defaultReader.DEFAULT);
  }

  /**
   * use to return Primitive Type, or String, Or JsonObject or JsonArray generated by a
   * ContainerFactory
   */
  public <T> T parse(Reader in, JsonReaderI<T> mapper) throws ParseException {
    this.base = mapper.base;
    //
    this.in = in;
    return super.parse(mapper);
  }

  protected void read() throws IOException {
    int i = in.read();
    c = (i == -1) ? (char) EOI : (char) i;
    pos++;
  }

  protected void readS() throws IOException {
    sb.append(c);
    int i = in.read();
    if (i == -1) {
      c = EOI;
    } else {
      c = (char) i;
      pos++;
    }
  }

  protected void readNoEnd() throws ParseException, IOException {
    int i = in.read();
    if (i == -1) throw new ParseException(pos - 1, ERROR_UNEXPECTED_EOF, "EOF");
    c = (char) i;
  }
}
