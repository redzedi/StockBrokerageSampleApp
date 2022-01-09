package test.suman.StockBrokerage.infra;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UUIDToStringAttributeConverter implements AttributeConverter<UUID, String> {

	@Override
	public String convertToDatabaseColumn(UUID attribute) {
		return Optional.of(attribute)
				.map(a->a.toString())
				.orElse(null);
	}

	@Override
	public UUID convertToEntityAttribute(String dbData) {
		return Optional.of(dbData)
				.map(d->UUID.fromString(d))
				.orElse(null);
	}

}
