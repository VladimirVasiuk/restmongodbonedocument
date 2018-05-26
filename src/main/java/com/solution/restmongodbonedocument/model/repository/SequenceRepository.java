package com.solution.restmongodbonedocument.model.repository;

import com.solution.restmongodbonedocument.model.entity.Sequence;
import org.springframework.data.repository.CrudRepository;

public interface SequenceRepository extends CrudRepository<Sequence, String> {
}
