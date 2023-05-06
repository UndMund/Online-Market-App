package org.example.validator.productValidator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductAttributesValidator {
    private static final ProductAttributesValidator INSTANCE = new ProductAttributesValidator();
    public static ProductAttributesValidator getInstance() {
        return INSTANCE;
    }

    private static final String PRICE_PATTERN =
            "\\d\\+";

    public boolean validatePrice(String price) {
        var  pattern = Pattern.compile(PRICE_PATTERN);
        var  matcher = pattern.matcher(price);
        return matcher.matches();
    }
}
