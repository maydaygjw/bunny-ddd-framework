package xyz.mayday.tools.bunny.ddd.context.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Profiles;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import com.google.common.collect.ImmutableMap;

public class EnvUtils {
    
    static final String PROFILES_YML = "/config/application-${component}-${env}.yml";
    static final String COMMON_YML = "/config/application-${component}.yml";
    static final List<String> ACCEPTED_PROFILES = Arrays.asList("prod", "stg", "dev", "local", "test");
    
    public static void loadResource(ConfigurableEnvironment environment, String condition, String component) {
        
        if (StringUtils.isBlank(condition) || Boolean.parseBoolean(environment.getProperty(condition))) {
            ClassPathResource resource = new ClassPathResource(StringSubstitutor.replace(COMMON_YML, ImmutableMap.of("component", component)));
            PropertySource<?> commonConfig = getCommonConfig(component + "CommonConfig", resource);
            if (Objects.nonNull(commonConfig)) {
                environment.getPropertySources().addLast(commonConfig);
            }
            ACCEPTED_PROFILES.stream().map(Profiles::of).filter(environment::acceptsProfiles)
                    .map(p -> new ClassPathResource(StringSubstitutor.replace(PROFILES_YML, ImmutableMap.of("component", component, "env", p))))
                    .forEach(classPathResource -> {
                        PropertySource<?> envConfigSource = getCommonConfig(component + "EnvConfig", classPathResource);
                        if (Objects.nonNull(envConfigSource)) {
                            environment.getPropertySources().addLast(envConfigSource);
                        }
                    });
        }
        
    }
    
    public static void loadResource(ConfigurableEnvironment environment, String component) {
        loadResource(environment, null, component);
    }
    
    private static PropertySource<?> getCommonConfig(String resourceName, ClassPathResource resource) {
        if (resource.exists()) {
            try {
                return new YamlPropertySourceLoader().load(resourceName, resource).get(0);
            } catch (IOException ignored) {
                
            }
        }
        return null;
    }
}
