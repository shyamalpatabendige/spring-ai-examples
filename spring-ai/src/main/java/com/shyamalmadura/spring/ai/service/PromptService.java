package com.shyamalmadura.spring.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PromptService {

    private final ChatClient chatClient;

    public String generateSentences(String language, String topic) {
        String userText = """
                 Start a sentence in {language} about this topic {topic} and ask the student to think about continuing the story to practice grammar and new words.
                 If the sentence is in Japanese,
                 always write back in Hiragana and provide the Romaji equivalent in brackets.
                 Also, translate it into English.
                 """;
        PromptTemplate pt = new PromptTemplate(userText);
        Prompt prompt = pt.create(Map.of("language", language, "topic", topic));

        ChatResponse response = chatClient.call(prompt);

        return response.getResult().getOutput().getContent();
    }
}
