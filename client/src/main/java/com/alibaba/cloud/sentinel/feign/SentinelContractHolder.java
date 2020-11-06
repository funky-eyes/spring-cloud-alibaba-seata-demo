package com.alibaba.cloud.sentinel.feign;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import feign.Contract;
import feign.MethodMetadata;

public class SentinelContractHolder implements Contract {
    private final Contract delegate;
    /**
     * map key is constructed by ClassFullName + configKey. configKey is constructed by {@link feign.Feign#configKey}
     */
    public final static Map<String, MethodMetadata> METADATA_MAP = new HashMap<>();

    public SentinelContractHolder(Contract delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<MethodMetadata> parseAndValidateMetadata(Class<?> targetType) {
        List<MethodMetadata> metadatas = delegate.parseAndValidateMetadata(targetType);
        metadatas.forEach(metadata -> METADATA_MAP.put(targetType.getName() + metadata.configKey(), metadata));
        return metadatas;
    }
}
