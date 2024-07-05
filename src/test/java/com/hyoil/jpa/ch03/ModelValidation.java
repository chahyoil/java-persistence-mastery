package com.hyoil.jpa.ch03;

import com.hyoil.jpa.ch03.validation.Item;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelValidation {

    @Test
    public void validateItem() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // 다음의 위반사항을 검사
        // auctionEnd 가 미래날짜여야 한다. (위반됨 : 현재날짜 지정)
        // name이 NotNull이고 2 ~ 255 길이가 허용 (테스트 통과)

        Item item = new Item();
        item.setName("Some Item");
        item.setAuctionEnd(new Date());

        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        ConstraintViolation<Item> violation = violations.iterator().next();
        String failedPropertyName =
                violation.getPropertyPath().iterator().next().getName();

        // Validation error, auction end date was not in the future!
        assertAll(() -> assertEquals(1, violations.size()),
                () -> assertEquals("auctionEnd", failedPropertyName),
                () -> {
                    if (Locale.getDefault().getLanguage().equals("en"))
                        assertEquals(violation.getMessage(), "must be a future date");
                });

    }

}