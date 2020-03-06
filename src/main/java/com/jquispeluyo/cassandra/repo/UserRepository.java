package com.jquispeluyo.cassandra.repo;

import com.jquispeluyo.cassandra.model.User;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface UserRepository extends CassandraRepository<User, UUID> {

}
