package xyz.mayday.tools.bunny.batch.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class JobExecutionVO {
    
    String jobName;
    
    Date initialTime;
    
    String batchStatus;
}
