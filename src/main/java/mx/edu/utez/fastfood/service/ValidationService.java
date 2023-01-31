package mx.edu.utez.fastfood.service;

import mx.edu.utez.fastfood.exception.ValidationException;

public class ValidationService {
    public static String[] validateCheckbox(String[] value, String name, Boolean required, Integer minChecked, Integer maxChecked) throws ValidationException {
        if (value == null || value.length == 0) if (required) throw new ValidationException("El campo \"" + name + "\" es requerido"); else return null;
        if (minChecked != null && value.length < minChecked) throw new ValidationException("El número de elementos seleccionados del campo \"" + name + "\" es menor al mínimo requerido");
        if (maxChecked != null && value.length > maxChecked) throw new ValidationException("El número de elementos seleccionados del campo \"" + name + "\" es mayor al máximo permitido");
        return value;
    }

    public static <T> T validateNumber(T value, String name, Boolean required, Double step, Double min, Double max) throws ValidationException {
        if (value == null) if (required) throw new ValidationException("El campo \"" + name + "\" es requerido"); else return null;
        if (step != null && ((double) value - (min != null ? min : 0)) % step != 0) throw new ValidationException("El valor de \"" + name + "\" es inválido");
        if (min != null && (double) value < min) throw new ValidationException("El valor de \"" + name + "\" es menor al mínimo requerido");
        if (max != null && (double) value > max) throw new ValidationException("El valor de \"" + name + "\" es mayor al máximo permitido");
        return value;
    }

    public static String validateText(String value, String name, Boolean required, Integer minLength, Integer maxLength, String pattern) throws ValidationException {
        if (value == null || value.isBlank()) if (required) throw new ValidationException("El campo \"" + name + "\" es requerido"); else return null;
        if (minLength != null && value.length() < minLength) throw new ValidationException("La longitud de \"" + name + "\" es menor a la mínima requerida");
        if (maxLength != null && value.length() > maxLength) throw new ValidationException("La longitud de \"" + name + "\" es mayor a la máxima permitida");
        if (pattern != null && !value.matches(pattern)) throw new ValidationException("El valor de \"" + name + "\" no cumple con el formato requerido");
        return value;
    }
}
