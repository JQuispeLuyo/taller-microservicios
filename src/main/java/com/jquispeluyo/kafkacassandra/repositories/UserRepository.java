package com.jquispeluyo.kafkacassandra.repositories;

import com.jquispeluyo.kafkacassandra.models.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import java.util.UUID;

public interface UserRepository extends CassandraRepository<User, UUID> {

}
