package com.billing.infrastructure.external;

import org.springframework.stereotype.Component;

@Component
public class FakeProductAdapter implements ExternalProductAdapter {

    @Override
    public boolean isEligibleForCharge(String namespace, String externalReferenceId) {
        return true; // aceita todos para simulação
    }
}
