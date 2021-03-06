package de.jlo.talendcomp.json.ops;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class JSONValue {
	
	private List<String> keyPath;
	private String attributeName = null;
	private Object value;
	private boolean isArrayValue = false;
	private int level = 0;
	
	public String getKeyPath(String delimiter) {
		if (delimiter == null) {
			delimiter = ".";
		}
		if (keyPath != null) {
			StringBuilder sb = new StringBuilder();
			boolean firstLoop = true;
			for (String key : keyPath) {
				if (firstLoop) {
					firstLoop = false;
				} else {
					if (key.startsWith("[") == false) {
						sb.append(delimiter);
					}
				}
				sb.append(key);
			}
			return sb.toString();
		} else {
			return null;
		}
	}
	
	public void setKeyPath(List<String> keyPath) {
		this.keyPath = keyPath;
		level = keyPath.size() - 1;
		isArrayValue = keyPath.get(keyPath.size() - 1).endsWith("]");
		for (int i = level; i >= 0; i--) {
			String name = keyPath.get(i);
			if (name.startsWith("[") == false) {
				attributeName = name;
				break;
			}
		}
	}
	
	public String getJsonPath() {
		return getKeyPath(".");
	}
	
	public Object getValue() {
		return value;
	}
	
	public String getValueString() {
		if (value != null) {
			if (value instanceof String) {
				return (String) value;
			} else if (value instanceof JsonNode) {
				if (((JsonNode) value).isTextual()) {
					return ((JsonNode) value).textValue();
				} else {
					return ((JsonNode) value).toString();
				}
			} else {
				return value.toString();
			}
		} else {
			return null;
		}
	}
	
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return getKeyPath(".") + ":" + getValueString() + " | attributeName=" + attributeName + " level=" + level + " isArrayValue=" + isArrayValue;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof JSONValue) {
			JSONValue v = (JSONValue) o;
			if (keyPath.equals(v.keyPath) && value.equals(v.value)) {
				return true;
			}
		}
		return false;
	}

	public boolean isArrayValue() {
		return isArrayValue;
	}

	public int getLevel() {
		return level;
	}

	public String getAttributeName() {
		return attributeName;
	}
	
}
