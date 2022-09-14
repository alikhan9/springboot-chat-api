package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SimpleMessage {
    private Long id;
    private String sender;
    private String receiver;
    private String message;
    private Timestamp date;
}
