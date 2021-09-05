package xyz.mayday.tools.bunny.batch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/** @author gejunwen */
@Data
@Entity
@Table(name = "batch_step_execution")
public class StepExecutionEntity {

  @Id Long id;

  String stepName;
}
