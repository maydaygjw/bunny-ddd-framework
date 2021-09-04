package xyz.mayday.tools.bunny.batch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.mayday.tools.bunny.batch.entity.StepExecutionEntity;

/** @author gejunwen */
@Repository
public interface StepExecutionRepository extends JpaRepository<StepExecutionEntity, Long> {}
