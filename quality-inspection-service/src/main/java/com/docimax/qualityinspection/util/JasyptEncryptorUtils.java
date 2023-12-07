package com.docimax.qualityinspection.util;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 * 明文加解密
 * @author Wangzh
 */
@Slf4j
public final class JasyptEncryptorUtils {
    private static final String SALT = "dimax";
    private static BasicTextEncryptor BASIC_TEXT_ENCRYPTOR = new BasicTextEncryptor();

    static {
        BASIC_TEXT_ENCRYPTOR.setPassword(SALT);
    }

    private JasyptEncryptorUtils() {
    }

    /**
     * 明文加密
     *
     * @param plaintext 明文字符串
     * @return 加密后额字符串
     */
    public static String encode(String plaintext) {
        if (StringUtil.isNullOrEmpty(plaintext)) {
            return plaintext;
        }
        return BASIC_TEXT_ENCRYPTOR.encrypt(plaintext);
    }

    /**
     * 解密
     *
     * @param ciphertext 加密字符串
     * @return 解密后的字符串
     */
    public static String decode(String ciphertext) {
        if (StringUtil.isNullOrEmpty(ciphertext)) {
            return ciphertext;
        }
        ciphertext = "ENC(" + ciphertext + ")";
        if (PropertyValueEncryptionUtils.isEncryptedValue(ciphertext)) {
            try {
                return PropertyValueEncryptionUtils.decrypt(ciphertext, BASIC_TEXT_ENCRYPTOR);
            } catch (Exception e) {
                // 解密失败，记录错误日志
                log.error("解密失败: {}", e.getMessage());
                // 返回未解密的值
                return "";
            }
        }
        return ciphertext;
    }
}
