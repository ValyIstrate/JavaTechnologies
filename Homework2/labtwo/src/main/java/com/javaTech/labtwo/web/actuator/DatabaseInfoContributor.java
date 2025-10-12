package com.javaTech.labtwo.web.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInfoContributor implements InfoContributor {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void contribute(Info.Builder builder) {
        String dbName = jdbcTemplate.queryForObject("SELECT DATABASE()", String.class);
        builder.withDetail("connectedDatabase", dbName);
    }
}
