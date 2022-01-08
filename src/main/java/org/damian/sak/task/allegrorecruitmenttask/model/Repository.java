package org.damian.sak.task.allegrorecruitmenttask.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Repository {

    private String name;
    private int rating;
    private String programmingLanguage;
    private int size;

}
