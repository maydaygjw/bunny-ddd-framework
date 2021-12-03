package xyz.mayday.tools.bunny.ddd.utils;

import java.util.List;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.core.diff.changetype.ValueChange;

public class DiffUtils {
    
    static final Javers JAVERS = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.SIMPLE).build();
    
    public static List<ValueChange> getValueChanges(Object o1, Object o2) {
        Javers javers = JaversBuilder.javers().build();
        Diff diff = javers.compare(o1, o2);
        return diff.getChangesByType(ValueChange.class);
    }
    
    public static <T> String getDiffSummaryString(T o1, T o2) {
        return JAVERS.compare(o1, o2).prettyPrint();
    }
}
