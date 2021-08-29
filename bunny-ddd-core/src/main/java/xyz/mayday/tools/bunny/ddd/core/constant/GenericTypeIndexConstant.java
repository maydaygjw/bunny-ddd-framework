package xyz.mayday.tools.bunny.ddd.core.constant;

public interface GenericTypeIndexConstant {

    class ServiceTypeIndex {
        public static final Integer IDX_ID = 0;
        public static final Integer IDX_DTO = 1;
        public static final Integer IDX_DAO = 2;
    }

    class ControllerTypeIndex {
        public static final Integer IDX_ID = 0;
        public static final Integer IDX_VO = 1;
        public static final Integer IDX_QUERY = 2;
        public static final Integer IDX_DTO = 3;
    }
}
