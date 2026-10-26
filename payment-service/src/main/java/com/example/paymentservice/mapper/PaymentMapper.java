
package com.example.paymentservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.paymentservice.dto.request.PaymentRequestDto;
import com.example.paymentservice.dto.response.PaymentResponseDto;
import com.example.paymentservice.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "transactionReference", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    Payment toEntity(PaymentRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "transactionReference", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(
            PaymentRequestDto dto,
            @MappingTarget Payment payment);

    PaymentResponseDto toResponseDto(Payment payment);
}
