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

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.Options;
import com.typesafe.config.Config;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Representation of {@linkplain WireMockServer}'s configurations stored in {@linkplain
 * KnotxWiremockExtension}.
 */
class KnotxMockConfig {
  public static final String MIMETYPE_AUTODETECT = "!autodetect";
  public static final String PATH_INHERIT = "!inherit";
  public static final int RANDOM_PORT = Options.DYNAMIC_PORT;

  final String name;
  final String requestPath;
  final String mimetype;
  final int port;

  KnotxMockConfig(String name, int port, String requestPath, String mimetype) {
    this.name = name;
    this.port = port;
    this.requestPath = requestPath;
    this.mimetype = mimetype;
  }

  KnotxMockConfig(String name, int port, String requestPath) {
    this.name = name;
    this.port = port;
    this.requestPath = requestPath;
    this.mimetype = MIMETYPE_AUTODETECT;
  }

  KnotxMockConfig(String name, int port) {
    this.name = name;
    this.port = port;
    this.requestPath = PATH_INHERIT;
    this.mimetype = MIMETYPE_AUTODETECT;
  }

  KnotxMockConfig(String name) {
    this.name = name;
    this.port = RANDOM_PORT;
    this.requestPath = PATH_INHERIT;
    this.mimetype = MIMETYPE_AUTODETECT;
  }

  KnotxMockConfig(KnotxMockConfig parent, int newPort) {
    this.name = parent.name;
    this.port = newPort;
    this.requestPath = parent.requestPath;
    this.mimetype = parent.mimetype;
  }

  public KnotxMockConfig(String fullName, Integer port, String urlMatching, String mimetype,
      Map<String, Object> headers) {

  }

  static KnotxMockConfig createMockConfig(Config config, String forClass, String service) {
    String base = KnotxWiremockExtension.WIREMOCK_NAMESPACE + "." + service;
    String fullName = forClass + service;
    Set<String> unwrapped = new HashSet<>(config.atKey(base).root().keySet());

    Integer port = RANDOM_PORT;
    String urlMatching = ".*";
    String methods = "GET";
    Map<String, Object> headers = null;
    String mimetype = MIMETYPE_AUTODETECT;

    if (config.hasPathOrNull(base + ".port")) {
      if (!config.getIsNull(base + ".port")) {
        port = config.getInt(base + ".port");
      }
    }
    if (config.hasPath(base + ".methods")) {
      methods = config.getString(base + ".methods");
    }
    if (config.hasPath(base + ".urlMatching")) {
      urlMatching = config.getString(base + ".urlMatching");
    }
    if (config.hasPath(base + ".headers")) {
      headers = config.getObject(base + ".headers").unwrapped();
    }
    if (config.hasPath(base + ".mimetype")) {
      mimetype = config.getString(base + ".mimetype");
    }

    return new KnotxMockConfig(fullName, port, urlMatching, mimetype, headers);
  }
}
