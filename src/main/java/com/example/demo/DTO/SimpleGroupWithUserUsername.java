package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SimpleGroupWithUserUsername {
    private Long id;
    private String name;
    private String owner;
}
