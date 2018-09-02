package com.ikeda.mydata.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ikeda.mydata.annotations.Phone;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="mydata")
public class MyData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@NotNull
	@Getter
	@Setter
	private long id;
	
	@Column(length = 50, nullable = false)
	@NotEmpty
	@Getter
	@Setter
	private String name;
	
	@Column(length = 200, nullable = true)
	@Email
	@Getter
	@Setter
	private String mail;
	
	@Column(nullable = true)
	@Min(value=0)
	@Max(value=200)
	@Getter
	@Setter
	private Integer age;

	@Column(nullable = true)
	@Phone(onlyNumber = true)
	@Getter
	@Setter
	private String memo;
	
}
