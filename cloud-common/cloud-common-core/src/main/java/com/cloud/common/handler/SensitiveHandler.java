package com.cloud.common.handler;

import com.cloud.common.enums.SensitiveType;
import java.util.regex.Pattern;

/**
 * @author local
 * @date 2025-09-22
 * @description  敏感信息处理
 */
public class SensitiveHandler {

    // 预编译正则，提升性能
    private static final Pattern PHONE_PATTERN = Pattern.compile("(\\d{3})\\d{4}(\\d{4})");
    private static final Pattern ID_CARD_PATTERN = Pattern.compile("(\\d{2})\\d{12}(\\d{4})");
    private static final Pattern BANK_CARD_PATTERN = Pattern.compile("(\\d{4})\\d{10,16}(\\d{4})");

    public static String mask(String value, SensitiveType type, String maskPattern, String maskValue) {
        if (value == null) {
            return null;
        }

        return switch (type) {
            case PHONE -> maskPhone(value);
            case ID_CARD -> maskIdCard(value);
            case BANK_CARD -> maskBankCard(value);
            case CUSTOM -> {
                if (maskPattern != null && !maskPattern.isEmpty() && maskValue != null) {
                    yield Pattern.compile(maskPattern).matcher(value).replaceAll(maskValue);
                }
                yield value;
            }
        };
    }

    private static String maskPhone(String phone) {
        if (phone == null || phone.length() != 11 || !phone.matches("1[3-9]\\d{9}")) {
            // 非法手机号原样返回
            return phone;
        }
        return PHONE_PATTERN.matcher(phone).replaceAll("$1****$2");
    }

    private static String maskIdCard(String idCard) {
        if (idCard == null || (idCard.length() != 18 && idCard.length() != 15)) {
            return idCard;
        }
        return ID_CARD_PATTERN.matcher(idCard).replaceAll("$1****$2");
    }

    private static String maskBankCard(String bankCard) {
        if (bankCard == null || bankCard.length() < 12) {
            return bankCard;
        }
        return BANK_CARD_PATTERN.matcher(bankCard).replaceAll("$1****$2");
    }
}
