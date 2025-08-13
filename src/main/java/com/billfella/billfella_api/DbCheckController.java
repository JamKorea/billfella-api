package com.billfella.billfella_api;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbCheckController {

    private final JdbcTemplate jdbc;

    public DbCheckController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/api/db-check")
    public Map<String, Object> dbCheck() {
        // 1) 단순 연결 테스트
        Integer one = jdbc.queryForObject("select 1", Integer.class);

        // 2) users 테이블 존재 확인 (Flyway V1 적용 여부)
        Boolean usersExists = jdbc.queryForObject(
                "select to_regclass('public.users') is not null", Boolean.class);

        return Map.of(
                "database", (one != null && one == 1) ? "up" : "down",
                "users_table", Boolean.TRUE.equals(usersExists) ? "present" : "missing"
        );
    }
}
