package com.fho.digitalpec.exception;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ErrorResponse {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String code;

    @JsonIgnoreProperties({"cause", "trace", "stackTrace", "localizedMessage", "suppressed"})
    private Throwable error;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ErrorDetail> details;

    @Builder.Default
    private ZonedDateTime datetime = ZonedDateTime.now();

    @Getter
    @Builder
    public static class ErrorDetail {
        private String path;
        private String message;
    }
}
