package com.javatech.lab4.services.utils;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Data
public class MailDetails {

    @Getter
    @Setter
    public static class HtmlTemplate {
        private String template;
        private Map<String, Object> properties;

        public HtmlTemplate(String template, Map<String, Object> properties) {
            this.template = template;
            this.properties = properties;
        }
    }

    private String to;
    private String subject;
    private HtmlTemplate htmlTemplate;
}