package com.cce.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResultDTO {
    String result;
    String output;
    String expected;
    String verdict;
}
