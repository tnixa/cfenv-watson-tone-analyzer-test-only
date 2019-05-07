package org.terrence.testapp.envprocessor;

import java.util.Map;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.core.CfService;
import io.pivotal.cfenv.spring.boot.CfEnvProcessor;
import io.pivotal.cfenv.spring.boot.CfEnvProcessorProperties;

public class WatsonToneAnalyzerCfEnvProcessor implements CfEnvProcessor {

    public WatsonToneAnalyzerCfEnvProcessor() {
        System.out.println("WatsonToneAnalyzerCfEnvProcessor built");
    }

    @Override
    public boolean accept(CfService service) {
        boolean match = service.existsByLabelStartsWith("tone_analyzer");
        System.out.println("Match [" + match + "] to service " + service.toString());
        return match;
    }

    @Override
    public CfEnvProcessorProperties getProperties() {
        return CfEnvProcessorProperties.builder().propertyPrefixes("tone_analyzer").serviceName("Tone_Analyzer")
                .build();
    }

    @Override
    public void process(CfCredentials cfCredentials, Map<String, Object> properties) {
        // set watsonVersion to date of the released watson spring boot starter
        // version 0.3.0 was released on 2018-06-22
        String watsonVersion = "2018-06-22";
        properties.put("watson.tone-analyzer.url", cfCredentials.getUri("http"));
        properties.put("watson.tone-analyzer.iam-api-key", cfCredentials.getString("apikey"));
        properties.put("watson.tone-analyzer.versionDate", watsonVersion);

    }
}