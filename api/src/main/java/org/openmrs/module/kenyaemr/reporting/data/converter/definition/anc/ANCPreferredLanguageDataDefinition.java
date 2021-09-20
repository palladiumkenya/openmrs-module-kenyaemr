package org.openmrs.module.kenyaemr.reporting.data.converter.definition.anc;

import org.openmrs.module.reporting.data.BaseDataDefinition;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDataDefinition;
import org.openmrs.module.reporting.definition.configuration.ConfigurationPropertyCachingStrategy;
import org.openmrs.module.reporting.evaluation.caching.Caching;

@Caching(strategy= ConfigurationPropertyCachingStrategy.class)
public class ANCPreferredLanguageDataDefinition extends BaseDataDefinition implements EncounterDataDefinition {
    public ANCPreferredLanguageDataDefinition() {
        super();
    }

    public ANCPreferredLanguageDataDefinition(String name) {
        super(name);
    }

    @Override
    public Class<?> getDataType() {
        return String.class;
    }
}
