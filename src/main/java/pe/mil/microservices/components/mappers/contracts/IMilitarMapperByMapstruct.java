package pe.mil.microservices.components.mappers.contracts;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import pe.mil.microservices.dto.requests.RegisterMilitarRequest;
import pe.mil.microservices.repositories.entities.MilitarEntity;

import static pe.mil.microservices.components.mappers.contracts.IMilitarMapperByMapstruct.CustomerFields.*;

@Mapper
public interface IMilitarMapperByMapstruct {

    IMilitarMapperByMapstruct MILITAR_MAPPER = Mappers
        .getMapper(IMilitarMapperByMapstruct.class);

    @Condition
    default boolean isNotEmpty(String value) {
        return value != null && value.length() > 0;
    }

    @Mapping(source = FIELD_MILITAR_ID, target = FIELD_MILITAR_ID)
    @Mapping(source = FIELD_MILITAR_CIP, target = FIELD_MILITAR_CIP)
    @Mapping(source = FIELD_MILITAR_PERSON, target = FIELD_MILITAR_PERSON)
    @Mapping(source = FIELD_MILITAR_GRADE, target = FIELD_MILITAR_GRADE)
    @Mapping(source = FIELD_MILITAR_SPECIALTY, target = FIELD_MILITAR_SPECIALTY)
    MilitarEntity mapMilitarEntityByRegisterMilitarRequest(RegisterMilitarRequest source);


    class CustomerFields {
        public static final String FIELD_MILITAR_ID = "militarId";
        public static final String FIELD_MILITAR_CIP = "cip";
        public static final String FIELD_MILITAR_PERSON = "person";
        public static final String FIELD_MILITAR_GRADE = "grade";
        public static final String FIELD_MILITAR_SPECIALTY = "specialty";
    }
}
