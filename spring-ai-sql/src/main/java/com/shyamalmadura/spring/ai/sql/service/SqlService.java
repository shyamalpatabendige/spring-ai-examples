package com.shyamalmadura.spring.ai.sql.service;

import com.shyamalmadura.spring.ai.sql.exception.SqlGenerationException;
import com.shyamalmadura.spring.ai.sql.record.Answer;
import com.shyamalmadura.spring.ai.sql.record.SqlRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

@Slf4j
@Service
public class SqlService {

    @Value("classpath:/schema.sql")
    private Resource shemaResource;
    private final PromptTemplate sqlPromptTemplate;
    private final ChatClient chatClient;
    private final JdbcTemplate jdbcTemplate;

    public SqlService(@Value("classpath:/sql-prompt-template.st") Resource sqlPromptTemplateResource,
                      ChatClient chatClient, JdbcTemplate jdbcTemplate) {
        this.chatClient = chatClient;
        this.jdbcTemplate= jdbcTemplate;
        this.sqlPromptTemplate = new PromptTemplate(sqlPromptTemplateResource);
    }


    public Answer sql(SqlRequest sqlRequest) throws IOException {
        String schema = shemaResource.getContentAsString(Charset.defaultCharset());
        Prompt prompt = sqlPromptTemplate.create(Map.of("question", sqlRequest.question(), "ddl", schema));
        String query = chatClient.call(prompt).getResult().getOutput().getContent();

        log.info(query);

        if (query.toLowerCase().startsWith("select")) {
            return new Answer(query, jdbcTemplate.queryForList(query));
        }

        throw new SqlGenerationException(query);
    }


}
