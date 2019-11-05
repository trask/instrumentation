package org.glowroot.instrumentation.netty;

import org.glowroot.instrumentation.api.Descriptor;

@Descriptor(
            id = "netty",
            name = "Netty",
            classes = {
                    NettyInstrumentation.class
            },
            collocate = true)
public class InstrumentationDescriptor {}
