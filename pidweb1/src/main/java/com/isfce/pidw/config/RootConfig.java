package com.isfce.pidw.config;

import java.util.regex.Pattern;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import com.isfce.pidw.config.RootConfig.WebPackage;


@Configuration

@ComponentScan(basePackages = { "com.isfce.pidw" }, 
//Exclusion du scanning les classes du package resultats.web
excludeFilters = {
		@Filter(type = FilterType.CUSTOM, value = WebPackage.class) 
		}
)
public class RootConfig {

	public static class WebPackage extends RegexPatternTypeFilter {
		public WebPackage() {
			super(Pattern.compile("com.isfce.pidw\\.web"));
		}
	}
}
