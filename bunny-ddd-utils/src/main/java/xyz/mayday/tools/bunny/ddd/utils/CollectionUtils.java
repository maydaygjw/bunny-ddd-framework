package xyz.mayday.tools.bunny.ddd.utils;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {
    
    public static Boolean isEmptyExt(Collection<?> collection) {
        
        if (org.apache.commons.collections.CollectionUtils.isEmpty(collection)) {
            return true;
        }
        return org.apache.commons.collections.CollectionUtils.isEmpty(collection.stream().filter(Objects::nonNull).collect(Collectors.toList()));
    }
    
    public static boolean isNotEmptyExt(Collection<?> collection) {
        return !isEmptyExt(collection);
    }
}
