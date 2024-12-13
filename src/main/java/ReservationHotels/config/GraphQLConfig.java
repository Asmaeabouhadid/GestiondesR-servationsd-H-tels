package ReservationHotels.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;

@Configuration
public class GraphQLConfig {

    @Bean
    public GraphQLScalarType dateScalar() {
        return ExtendedScalars.Date;
    }
}
