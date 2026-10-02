package com.Niraj17.NirajAI.observability;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class ChatMetrics {

    private final Counter requests;
    private final Counter completions;
    private final Counter failures;

    public ChatMetrics(MeterRegistry meterRegistry) {
        requests = meterRegistry.counter("devtutor.chat.requests");
        completions = meterRegistry.counter("devtutor.chat.completions");
        failures = meterRegistry.counter("devtutor.chat.failures");
    }

    public void requested() { requests.increment(); }
    public void completed() { completions.increment(); }
    public void failed() { failures.increment(); }
}
