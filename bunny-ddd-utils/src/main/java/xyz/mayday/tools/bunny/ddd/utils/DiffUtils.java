package xyz.mayday.tools.bunny.ddd.utils;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;

import java.util.List;

public class DiffUtils {

    public static List<ValueChange> getValueChanges(Object o1, Object o2) {
        Javers javers = JaversBuilder.javers().build();
        Diff diff = javers.compare(o1, o2);
        return diff.getChangesByType(ValueChange.class);
    }

}
