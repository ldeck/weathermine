package balloon.reading.types;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Observatory {
	AU(TemperatureScale.celsius, DistanceUnit.km),
	US(TemperatureScale.fahrenheit, DistanceUnit.miles),
	FR(TemperatureScale.kelvin, DistanceUnit.m),
	Other(TemperatureScale.kelvin, DistanceUnit.km);

	public final TemperatureScale scale;
	public final DistanceUnit distanceUnit;

	Observatory (TemperatureScale scale, DistanceUnit distanceUnit) {
		this.scale = scale;
		this.distanceUnit = distanceUnit;
	}

	private static final Map<String, Observatory> LABS;
	static {
		LABS = Arrays.stream(values()).collect(Collectors.toMap(Observatory::name,(o)->o));
	}

	public static Observatory findByCode(String code) {
		return LABS.getOrDefault(code.toUpperCase(), Other);
	}
}
