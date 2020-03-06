package com.jquispeluyo.cassandra.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("users")
@Data
public class User {

	@PrimaryKey
	private UUID user_id;
	private String fname ;
	private String lname;

}