package com.Niraj17.NirajAI.ai;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class LocalTutorProvider implements AIProvider {

    @Override
    public String generate(AIPrompt prompt) {
        return "Let's work through this step by step. You asked: " + prompt.userMessage()
                + "\n\nStart by identifying the core concept, then connect it to a small example. "
                + "I can provide a deeper implementation walkthrough when an AI provider is configured.";
    }
}
