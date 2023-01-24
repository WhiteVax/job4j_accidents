package ru.accidents.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Подключает DelegatingFilterProxy. Главный фильтр, в котором идёт обработка запросов.
 * Tomcat автоматически определяет такой класс и подключает эти фильтры
 */
public class SecurityInit extends AbstractSecurityWebApplicationInitializer {
}
