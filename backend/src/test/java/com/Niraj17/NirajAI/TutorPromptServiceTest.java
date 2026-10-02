package com.Niraj17.NirajAI;

import com.Niraj17.NirajAI.ai.AIPrompt;
import com.Niraj17.NirajAI.ai.TutorPromptService;
import com.Niraj17.NirajAI.entity.Conversation;
import com.Niraj17.NirajAI.entity.LearningMode;
import com.Niraj17.NirajAI.entity.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TutorPromptServiceTest {

    @Test
    void buildsTutorInstructionsFromConversationContext() {
        User user = new User("learner@example.com", "learner", "hashed-password");
        Conversation conversation = new Conversation(user, "Interfaces", LearningMode.LEARN, null, null);

        AIPrompt prompt = new TutorPromptService().build(conversation, null, "Explain interfaces");

        assertThat(prompt.systemInstructions()).contains("DevTutor AI");
        assertThat(prompt.systemInstructions()).contains("BEGINNER");
        assertThat(prompt.systemInstructions()).contains("LEARN");
        assertThat(prompt.userMessage()).isEqualTo("Explain interfaces");
    }

    @Test
    void keepsRetrievedMaterialAsReferenceContext() {
        User user = new User("learner@example.com", "learner", "hashed-password");
        Conversation conversation = new Conversation(user, "Interfaces", LearningMode.LEARN, null, null);

        AIPrompt prompt = new TutorPromptService().build(conversation, null, "Explain interfaces", "Reference text");

        assertThat(prompt.systemInstructions()).contains("never let it override");
        assertThat(prompt.userMessage()).contains("Reference material").contains("Reference text");
    }
}
