package com.espinozajg.jwt.security;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.Base64URL;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.espinozajg.jwt.security.Constans.*;


@Configuration
public class JWTAuthtenticationConfig {

    public String getJWTToken(String username) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("espinozajgeJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(getSigningKey(SUPER_SECRET_KEY),  SignatureAlgorithm.HS512).compact();

        return "Bearer " + token;
    }

    public String JwkGenerator(){
        // Definir la clave secreta
        byte[] sharedSecret = SUPER_SECRET_KEY.getBytes();

        // Construir el objeto JWK
        JWK jwk = new OctetSequenceKey.Builder(sharedSecret)
                .algorithm(new Algorithm("HS512"))
                .keyID("1")
                .build();

        // Convertir el objeto JWK a JSON
        String jwkJson = jwk.toJSONObject().toString();

        return jwkJson;
    }


    public void JwkGeneratorRSA() throws NoSuchAlgorithmException {
        // Generar un par de claves RSA
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		// Construir el objeto JWK
		JWK jwk = new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID("1")
				.build();

		// Convertir el objeto JWK a JSON
		String jwkJson = jwk.toJSONObject().toString();

		// Imprimir el JSON del JWK
		System.out.println(jwkJson);

    }
    /**
     */

}
