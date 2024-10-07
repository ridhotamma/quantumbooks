package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.InvoiceDto;
import com.quantumbooks.core.entity.Invoice;
import com.quantumbooks.core.entity.Customer;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface InvoiceMapper {
    @Mapping(target = "customer", source = "customerID")
    Invoice toEntity(InvoiceDto invoiceDto);

    @Mapping(target = "customerID", source = "customer.customerID")
    InvoiceDto toDto(Invoice invoice);

    @Mapping(target = "customer", source = "customerID")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInvoiceFromDto(InvoiceDto invoiceDto, @MappingTarget Invoice invoice);

    default Customer customerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setCustomerID(id);
        return customer;
    }
}