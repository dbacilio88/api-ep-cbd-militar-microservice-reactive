package pe.mil.microservices.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProcessConstants {

    public static final String PROCESS_TYPE_STRING = "String";
    public static final String PARAM_COMPONENT_UUID = "String";
    public static final String PARAMETER_EMPTY_VALUE = "";
    public static final String PARAMETER_ACTUATOR_PATH_CONTAIN_VALUE = "actuator";
    public static final String MICROSERVICE_PATH_CONTEXT = "";
    public static final String MICROSERVICE_MILITAR_PATH = MICROSERVICE_PATH_CONTEXT + "/militares";
    public static final String GET_MILITAR_PATH = "";
    public static final String SAVE_MILITAR_PATH = "";
    public static final String SEARCH_MILITAR_PATH = "/search";
    public static final String PAGES_MILITAR_PATH = "/pageable";
    public static final String GET_MILITAR_ID_PATH = "/{militarId}";
    public static final String UPDATE_MILITAR_PATH = "";

    public static final String FIND_ALL_MILITAR_LOG_METHOD = "find.militares.method";
    public static final String FIND_BY_ID_MILITAR_LOG_METHOD = "findById.militares.method";
    public static final String SAVE_MILITAR_LOG_METHOD = "save.militares.method";
    public static final String UPDATE_MILITAR_LOG_METHOD = "update.militares.method";
    public static final String MAPSTRUCT_COMPONENT_MODEL_CONFIGURATION = "spring";
}