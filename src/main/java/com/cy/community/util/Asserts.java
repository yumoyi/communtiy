package com.cy.community.util;

import com.cy.community.exception.CustomizeException;
import com.cy.community.exception.ICustomerErrorCode;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @author cy
 * @since 2019-11-10 14:14
 */
public class Asserts extends Assert {

    public static void isNull(@Nullable Object object, ICustomerErrorCode errorCode) {
        if (Objects.isNull(object)) {
            throw new CustomizeException(errorCode);
        }
    }


}
