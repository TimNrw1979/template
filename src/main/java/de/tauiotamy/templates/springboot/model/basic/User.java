package de.tauiotamy.templates.springboot.model.basic;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

	protected String userName;
	protected String surName;
	protected String foreName;
	protected String email;
	protected String sub;

}
