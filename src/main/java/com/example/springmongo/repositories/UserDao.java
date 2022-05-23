package com.example.springmongo.repositories;

import com.example.springmongo.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface UserDao extends MongoRepository<User, Integer> {

}
