package com.annualLeave.framework.conifg;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JPQLMetadataBuilder implements MetadataBuilderContributor {

	//TODO : DB Ozel fonksiyonlari tanimlamak icin kullaniliyor
	@Override
	public void contribute(MetadataBuilder metadataBuilder) {
		metadataBuilder.applySqlFunction("ARRAY_TO_STRING", new SQLFunctionTemplate(StandardBasicTypes.STRING, "array_to_string(array(?1), ', ')"));
		metadataBuilder.applySqlFunction("ROUND_NMBR", new SQLFunctionTemplate(StandardBasicTypes.STRING, "round((?1)::numeric,2)"));
		metadataBuilder.applySqlFunction("DATE_PART", new SQLFunctionTemplate(StandardBasicTypes.STRING, "date_part(cast(?1 as text), ?2)"));
		metadataBuilder.applySqlFunction("TRANSLATE_UPPER_TR", new SQLFunctionTemplate(StandardBasicTypes.STRING, "translate(upper(cast(?1 as text)), 'ıİÖÇÜŞĞ -.*', 'IIOCUSG')"));
		metadataBuilder.applySqlFunction("INTERVAL", new SQLFunctionTemplate(StandardBasicTypes.STRING, "interval ?1"));
		metadataBuilder.applySqlFunction("UNNEST_INT", new SQLFunctionTemplate(StandardBasicTypes.STRING, "unnest(cast(string_to_array(?1,  ',') as BIGINT[]))"));
	}
}
