package top.lovelily.base;

import java.lang.instrument.Instrumentation;

/**
 * Desc: InstrumentationAgent
 * Author: xuhe
 * Date: 2019/8/6 2:22 PM
 * Version: 1.0
 */
public class InstrumentationAgent {
    private static volatile Instrumentation globalInstrumentation;

    public static void premain(final String agentArgs, final Instrumentation inst) {
        globalInstrumentation = inst;
    }

    public static long getObjectSize(final Object object) {
        if (globalInstrumentation == null) {
            throw new IllegalStateException("Agent not initialized.");
        }
        return globalInstrumentation.getObjectSize(object);
    }
}