package com.dalancon.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dalancon on 2019/5/18.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ApiFactory {
}
