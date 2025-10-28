package com.billing.infrastructure.external;

public interface ExternalProductAdapter {
    boolean isEligibleForCharge(String namespace, String externalReferenceId);
}
