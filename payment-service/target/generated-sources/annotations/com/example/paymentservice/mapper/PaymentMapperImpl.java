package com.example.paymentservice.mapper;

import com.example.paymentservice.dto.request.PaymentRequestDto;
import com.example.paymentservice.dto.response.PaymentResponseDto;
import com.example.paymentservice.entity.Payment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-20T18:35:52+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260528-0407, environment: Java 25.0.3 (Eclipse Adoptium)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment toEntity(PaymentRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Payment.PaymentBuilder payment = Payment.builder();

        payment.amount( dto.getAmount() );
        payment.orderId( dto.getOrderId() );
        payment.paymentMethod( dto.getPaymentMethod() );

        return payment.build();
    }

    @Override
    public void updateEntity(PaymentRequestDto dto, Payment payment) {
        if ( dto == null ) {
            return;
        }

        payment.setAmount( dto.getAmount() );
        payment.setPaymentMethod( dto.getPaymentMethod() );
    }

    @Override
    public PaymentResponseDto toResponseDto(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();

        paymentResponseDto.setAmount( payment.getAmount() );
        paymentResponseDto.setCreatedDate( payment.getCreatedDate() );
        paymentResponseDto.setId( payment.getId() );
        paymentResponseDto.setOrderId( payment.getOrderId() );
        paymentResponseDto.setPaymentMethod( payment.getPaymentMethod() );
        paymentResponseDto.setPaymentStatus( payment.getPaymentStatus() );
        paymentResponseDto.setTransactionReference( payment.getTransactionReference() );
        paymentResponseDto.setUpdatedDate( payment.getUpdatedDate() );
        paymentResponseDto.setVersion( payment.getVersion() );

        return paymentResponseDto;
    }
}
