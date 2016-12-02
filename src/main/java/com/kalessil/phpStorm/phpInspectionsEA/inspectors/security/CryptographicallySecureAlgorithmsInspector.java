package com.kalessil.phpStorm.phpInspectionsEA.inspectors.security;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.psi.elements.ConstantReference;
import com.kalessil.phpStorm.phpInspectionsEA.openApi.BasePhpElementVisitor;
import com.kalessil.phpStorm.phpInspectionsEA.openApi.BasePhpInspection;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/*
 * This file is part of the Php Inspections (EA Extended) package.
 *
 * (c) Vladimir Reznichenko <kalessil@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

public class CryptographicallySecureAlgorithmsInspector extends BasePhpInspection {

    final private static Map<String, String> constants = new HashMap<>();
    static {
        /* known bugs */
        constants.put("MCRYPT_RIJNDAEL_192",    "mcrypt's MCRYPT_RIJNDAEL_192 is not AES compliant, MCRYPT_RIJNDAEL_128 should be used instead");
        constants.put("MCRYPT_RIJNDAEL_256",    "mcrypt's MCRYPT_RIJNDAEL_256 is not AES compliant, MCRYPT_RIJNDAEL_128 + 256-bit key should be used instead");
        /* weak algorithms */
        constants.put("MCRYPT_3DES",            "3DES has known vulnerabilities, consider using MCRYPT_RIJNDAEL_128 instead");
        constants.put("MCRYPT_DES",             "DES has known vulnerabilities, consider using MCRYPT_RIJNDAEL_128 instead");
        constants.put("MCRYPT_RC2",             "RC2 has known vulnerabilities, consider using MCRYPT_RIJNDAEL_128 instead");
        constants.put("MCRYPT_RC4",             "RC4 has known vulnerabilities, consider using MCRYPT_RIJNDAEL_128 instead");
        constants.put("OPENSSL_CIPHER_3DES",    "3DES has known vulnerabilities, consider using AES-128-* instead");
        constants.put("OPENSSL_CIPHER_DES",     "DES has known vulnerabilities, consider using AES-128-* instead");
        constants.put("OPENSSL_CIPHER_RC2_40",  "RC2 has known vulnerabilities, consider using AES-128-* instead");
        constants.put("OPENSSL_CIPHER_RC2_64",  "RC2 has known vulnerabilities, consider using AES-128-* instead");

        /*
            Constants:
                OPENSSL_ALGO_SHA1, OPENSSL_ALGO_MD5, OPENSSL_ALGO_MD4, OPENSSL_ALGO_MD2
            Functions:
                md5|sha1|crc32
            Strings (possibly only resolved as string literals):
                '(tripledes)|(des3)|(des(-(ede|ede3))?(-(cbc|cfb|ecb|cfb1|cfb8|ofb))?)'
                'sha[01]?'
                'md[245]'
                'rc2(-(40|64))?(-(cbc|cfb|ecb|ofb))?'
                'rc4(-40)?'
         */
    }

    @NotNull
    public String getShortName() {
        return "CryptographicallySecureAlgorithms";
    }

    @Override
    @NotNull
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new BasePhpElementVisitor() {
            public void visitPhpConstantReference(ConstantReference reference) {
                final String name = reference.getName();
                if (!StringUtil.isEmpty(name) && constants.containsKey(name)) {
                    holder.registerProblem(reference, constants.get(name), ProblemHighlightType.GENERIC_ERROR);
                }
            }
        };
    }
}
