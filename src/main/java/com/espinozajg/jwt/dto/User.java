package com.espinozajg.jwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.sql.DataSourceDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@JsonIgnore
	private String user;

	@JsonIgnore
	private String pass;

	private String token;

}
