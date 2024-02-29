package com.shyamalmadura.spring.ai.controller;

import com.shyamalmadura.spring.ai.api.JokeResponse;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    private String promptTemplate;

    @Autowired
    public ChatController(ChatClient chatClient, @Value("${chat.promptTemplate}") String promptTemplate) {
        this.chatClient = chatClient;
        this.promptTemplate = promptTemplate;
    }

    @GetMapping("/joke/ai/generate")
    public JokeResponse generate(@RequestParam(value = "subject") String subject) {
        BeanOutputParser<JokeResponse> parser =
                new BeanOutputParser<>(JokeResponse.class);
        String format = parser.getFormat();

        PromptTemplate pt = new PromptTemplate(promptTemplate);
        Prompt renderedPrompt = pt.create(Map.of("subject", subject, "format", format));

        ChatResponse response = chatClient.call(renderedPrompt);

        Usage usage = response.getMetadata().getUsage();
        System.out.println("Usage: " + usage.getPromptTokens() + " " + usage.getGenerationTokens() + "; " + usage.getTotalTokens());

        return parser.parse(response.getResult().getOutput().getContent());
    }
}