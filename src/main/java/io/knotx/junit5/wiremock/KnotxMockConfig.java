/*
 * Copyright (C) 2018 Knot.x Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.knotx.junit5.wiremock;

import com.github.tomakehurst.wiremock.core.Options;

class KnotxMockConfig {
  public static final String MIMETYPE_AUTODETECT = "!autodetect";
  public static final String PATH_INHERIT = "!inherit";
  public static final int RANDOM_PORT = Options.DYNAMIC_PORT;

  final String name;
  final int port;
  final String requestPath;
  final String mimetype;

  public KnotxMockConfig(String name, int port, String requestPath, String mimetype) {
    this.name = name;
    this.port = port;
    this.requestPath = requestPath;
    this.mimetype = mimetype;
  }

  public KnotxMockConfig(String name, int port, String requestPath) {
    this.name = name;
    this.port = port;
    this.requestPath = requestPath;
    this.mimetype = MIMETYPE_AUTODETECT;
  }

  public KnotxMockConfig(String name, int port) {
    this.name = name;
    this.port = port;
    this.requestPath = PATH_INHERIT;
    this.mimetype = MIMETYPE_AUTODETECT;
  }

  public KnotxMockConfig(String name) {
    this.name = name;
    this.port = RANDOM_PORT;
    this.requestPath = PATH_INHERIT;
    this.mimetype = MIMETYPE_AUTODETECT;
  }
}
