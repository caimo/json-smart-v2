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

import net.minidev.json.JSONValue;
import net.minidev.json.writer.JsonReaderI;

/**
 * Parser for JSON text. Please note that JSONParser is NOT thread-safe.
 *
 * @author Uriel Chemouni &lt;uchemouni@gmail.com&gt;
 */
class JSONParserString extends JSONParserMemory {
  private String in;

  public JSONParserString(int permissiveMode) {
    super(permissiveMode);
  }

  public JSONParserString(String in, int permissiveMode) {
    super(permissiveMode);
    this.in = in;
    this.len = in.length();
  }

  /**
   * use to return Primitive Type, or String, Or JsonObject or JsonArray generated by a
   * ContainerFactory
   */
  public Object parse(String in) throws ParseException {
    return parse(in, JSONValue.defaultReader.DEFAULT);
  }

  //
  //
  //
  //
  //
  //
  //

  /**
   * use to return Primitive Type, or String, Or JsonObject or JsonArray generated by a
   * ContainerFactory
   */
  public <T> T parse(String in, JsonReaderI<T> mapper) throws ParseException {
    this.base = mapper.base;
    this.in = in;
    this.len = in.length();
    return parse(mapper);
  }

  protected void extractString(int beginIndex, int endIndex) {
    xs = in.substring(beginIndex, endIndex);
  }

  protected void extractStringTrim(int start, int stop) {
    while (start < stop - 1 && Character.isWhitespace(in.charAt(start))) {
      start++;
    }
    while (stop - 1 > start && Character.isWhitespace(in.charAt(stop - 1))) {
      stop--;
    }
    extractString(start, stop);
  }

  protected int indexOf(char c, int pos) {
    return in.indexOf(c, pos);
  }

  /** Read next char or END OF INPUT */
  protected void read() {
    if (++pos >= len) this.c = EOI;
    else this.c = in.charAt(pos);
  }

  /** Same as read() in memory parsing */
  protected void readS() {
    if (++pos >= len) this.c = EOI;
    else this.c = in.charAt(pos);
  }

  /** read data can not be EOI */
  protected void readNoEnd() throws ParseException {
    if (++pos >= len) {
      this.c = EOI;
      throw new ParseException(pos - 1, ERROR_UNEXPECTED_EOF, "EOF");
    } else this.c = in.charAt(pos);
  }
}
