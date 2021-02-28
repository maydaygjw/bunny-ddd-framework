package xyz.mayday.tools.bunny.batch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Date;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class JobExecutionVO {

    String jobName;

    Date initialTime;

    String batchStatus;

}
