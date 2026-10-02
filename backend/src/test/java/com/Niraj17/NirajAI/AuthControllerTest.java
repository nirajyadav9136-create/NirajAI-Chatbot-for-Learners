package com.Niraj17.NirajAI;

import com.Niraj17.NirajAI.repository.RefreshTokenRepository;
import com.Niraj17.NirajAI.repository.ConversationRepository;
import com.Niraj17.NirajAI.repository.MessageRepository;
import com.Niraj17.NirajAI.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase() {
      messageRepository.deleteAll();
      conversationRepository.deleteAll();
        refreshTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void registerReturnsTokensAndAccessTokenAuthorizesCurrentUser() throws Exception {
        String response = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "learner@example.com",
                                  "username": "learner",
                                  "password": "strong-password"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String accessToken = new com.fasterxml.jackson.databind.ObjectMapper()
                .readTree(response)
                .get("accessToken")
                .asText();

        mockMvc.perform(get("/api/auth/me")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("learner@example.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

            @Test
            void profileUpdatesAndPasswordChangeAreOwnedByAuthenticatedUser() throws Exception {
            String response = mockMvc.perform(post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                          "email": "profile@example.com",
                          "username": "profile-user",
                          "password": "old-password"
                        }
                        """))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
            String accessToken = new com.fasterxml.jackson.databind.ObjectMapper()
                .readTree(response)
                .get("accessToken")
                .asText();

            mockMvc.perform(get("/api/users/me/profile")
                    .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.experienceLevel").value("BEGINNER"))
                .andExpect(jsonPath("$.learningGoal").value("GENERAL_LEARNING"));

            mockMvc.perform(patch("/api/users/me/profile")
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                          "displayName": "Profile Learner",
                          "experienceLevel": "INTERMEDIATE",
                          "learningGoal": "PROJECT_BUILDING"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("Profile Learner"))
                .andExpect(jsonPath("$.experienceLevel").value("INTERMEDIATE"));

            mockMvc.perform(post("/api/auth/change-password")
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                          "currentPassword": "old-password",
                          "newPassword": "new-password"
                        }
                        """))
                .andExpect(status().isNoContent());

            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                          "email": "profile@example.com",
                          "password": "new-password"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
            }

                @Test
                void conversationsAndMessagesArePersistedAndScopedToOwner() throws Exception {
              String response = mockMvc.perform(post("/api/auth/register")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("""
                    {
                      "email": "chat@example.com",
                      "username": "chat-user",
                      "password": "chat-password"
                    }
                    """))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
              String accessToken = new com.fasterxml.jackson.databind.ObjectMapper().readTree(response).get("accessToken").asText();

              String conversation = mockMvc.perform(post("/api/conversations")
                  .header("Authorization", "Bearer " + accessToken)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("""
                    {"title": "Java help", "learningMode": "LEARN"}
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Java help"))
                .andReturn().getResponse().getContentAsString();
              String conversationId = new com.fasterxml.jackson.databind.ObjectMapper().readTree(conversation).get("id").asText();

              mockMvc.perform(post("/api/conversations/" + conversationId + "/messages")
                  .header("Authorization", "Bearer " + accessToken)
                  .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Explain interfaces\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("USER"));

              mockMvc.perform(get("/api/conversations/" + conversationId)
                  .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messages[0].content").value("Explain interfaces"));
                }
}
