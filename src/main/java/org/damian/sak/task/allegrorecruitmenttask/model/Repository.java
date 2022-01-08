package org.damian.sak.task.allegrorecruitmenttask.model;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("stargazers_count") private int rating;
    @SerializedName("language")private String programmingLanguage;
    private int size;

}
