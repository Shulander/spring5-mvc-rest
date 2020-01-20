package us.vicentini.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import us.vicentini.api.v1.model.CustomerDTO;
import us.vicentini.domain.Customer;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDTO(Customer category);

    Customer customerDTOToCustomer(CustomerDTO category);
}
