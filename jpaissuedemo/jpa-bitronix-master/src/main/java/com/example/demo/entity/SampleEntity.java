package com.example.demo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name="zpath_table")
public class SampleEntity {

	@Id
	@Column(name="sequence")
	private int id;

	@Column(name="name")
	private String Name;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		SampleEntity cloud = (SampleEntity) o;
		return Objects.equals(id, cloud.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
