package cn.com.trueway.base.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD,ElementType.TYPE })
public @interface TrueAnnotation {
	//名称
	String name();
	
	//当值为id时候表示该属性是id
	public String id() default "1";
}
