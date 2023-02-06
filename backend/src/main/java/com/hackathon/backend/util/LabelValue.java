package com.hackathon.backend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelValue {
    private Long id;
    private String name;

    public static LabelValue of(Long id, String name) {
        return new LabelValue(id, name);
    }
}
