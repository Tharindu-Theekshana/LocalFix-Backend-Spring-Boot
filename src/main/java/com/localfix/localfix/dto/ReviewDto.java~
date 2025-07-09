package com.localfix.localfix.dto;

import com.localfix.localfix.model.Profile;
import com.localfix.localfix.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private int id;
    private double rating;
    private String comment;
    private int customerId;
    private int profileId;
}
