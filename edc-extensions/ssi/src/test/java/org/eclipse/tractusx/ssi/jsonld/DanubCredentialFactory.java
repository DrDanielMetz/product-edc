package org.eclipse.tractusx.ssi.jsonld;

import com.danubetech.verifiablecredentials.CredentialSubject;
import lombok.SneakyThrows;

import java.net.URI;
import java.util.Date;
import java.util.List;

public class DanubCredentialFactory {

    @SneakyThrows
    public static com.danubetech.verifiablecredentials.VerifiablePresentation getTestDanubVP(){
        com.danubetech.verifiablecredentials.VerifiablePresentation.Builder<? extends com.danubetech.verifiablecredentials.VerifiablePresentation.Builder<?>>
                builder = com.danubetech.verifiablecredentials.VerifiablePresentation
                .builder();
        return builder
                .defaultContexts(true)
                .forceContextsArray(true)
                .forceTypesArray(true)
                .id(new URI("http://example.edu/presentation/1872"))
                .types(List.of("VerifiablePresentation", "TestPresentation"))
                .holder(new URI("https://example.edu/holder/565049"))
                .verifiableCredential(getTestDanubVC())
                .ldProof(null) // set to null, as presentation will be used within JWT
                .build();
    }

    @SneakyThrows
    public static com.danubetech.verifiablecredentials.VerifiableCredential getTestDanubVC() {
        com.danubetech.verifiablecredentials.VerifiableCredential.Builder<? extends com.danubetech.verifiablecredentials.VerifiableCredential.Builder<?>>
                builder = com.danubetech.verifiablecredentials.VerifiableCredential
                .builder();
        return builder
                .defaultContexts(true)
                .forceContextsArray(true)
                .forceTypesArray(true)
                .id(new URI("http://example.edu/credentials/1872"))
                .types(List.of("VerifiableCredential", "TestCredential"))
                .issuer(new URI("https://example.edu/issuers/565049"))
                .issuanceDate(new Date())
                .credentialSubject(getSubject())
                .ldProof(null) // set to null, as presentation will be used within JWT
                .build();
    }


    @SneakyThrows
    private static CredentialSubject getSubject(){
        CredentialSubject subject = CredentialSubject
                .builder()
                .id(new URI("did:example:c276e12ec21ebfeb1f712ebc6f1"))
                .build();
        return subject;
    }
}
