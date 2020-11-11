package com.laomei.sidecar;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SidecarConfiguration.class)
public @interface EnableEnhanceSidecar {
}
