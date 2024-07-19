package com.bist.backendmodule.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AopConfiguration class configures Aspect-Oriented Programming (AOP) settings for the application.
 * It enables support for handling components marked with AspectJ's @Aspect annotation.
 */
@Configuration
@EnableAspectJAutoProxy
public class AopConfiguration {
}
