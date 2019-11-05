/**
 * Copyright 2016-2019 the original author or authors.
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
package org.glowroot.instrumentation.test.matrix;

import static org.glowroot.instrumentation.test.matrix.JavaVersion.JAVA6;
import static org.glowroot.instrumentation.test.matrix.JavaVersion.JAVA7;
import static org.glowroot.instrumentation.test.matrix.JavaVersion.JAVA8;

public class Netty {

    private static final String MODULE_PATH = "instrumentation/netty";

    public static void main(String[] args) throws Exception {
        if (args.length == 1 && args[0].equals("short")) {
            runShort();
        } else {
            runAll();
        }
    }

    static void runShort() throws Exception {
        run("4.0.0.Final");
        runHttp2("4.1.0.Final");
    }

    static void runAll() throws Exception {
        for (int i = 0; i <= 56; i++) {
            run("4.0." + i + ".Final");
        }
        for (int i = 1; i <= 7; i++) {
            run("4.1.0.CR" + i);
        }
        for (int i = 0; i <= 32; i++) {
            if (i == 28) {
                // Netty 4.1.28 fails on Java 6, see https://github.com/netty/netty/issues/8166
                runJava7("4.1." + i + ".Final");
            } else {
                run("4.1." + i + ".Final");
            }
            runHttp2("4.1." + i + ".Final");
        }
    }

    private static void run(String version) throws Exception {
        Util.updateLibVersion(MODULE_PATH, "netty.version", version);
        Util.runTest(MODULE_PATH, "NettyIT", JAVA8, JAVA7, JAVA6);
    }

    private static void runJava7(String version) throws Exception {
        Util.updateLibVersion(MODULE_PATH, "netty.version", version);
        Util.runTest(MODULE_PATH, "NettyIT", JAVA8, JAVA7);
    }

    private static void runHttp2(String version) throws Exception {
        Util.updateLibVersion(MODULE_PATH, "netty.version", version);
        Util.runTest(MODULE_PATH, "NettyIT", "netty-http2", JAVA8, JAVA7);
    }
}
