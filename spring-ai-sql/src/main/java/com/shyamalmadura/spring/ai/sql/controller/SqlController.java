package com.shyamalmadura.spring.ai.sql.controller;

import com.shyamalmadura.spring.ai.sql.record.Answer;
import com.shyamalmadura.spring.ai.sql.record.SqlRequest;
import com.shyamalmadura.spring.ai.sql.service.SqlService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class SqlController {

    private final SqlService sqlService;

    @PostMapping(path="/sql")
    public Answer sql(@RequestBody SqlRequest sqlRequest) throws IOException {
        return sqlService.sql(sqlRequest);
    }
}
