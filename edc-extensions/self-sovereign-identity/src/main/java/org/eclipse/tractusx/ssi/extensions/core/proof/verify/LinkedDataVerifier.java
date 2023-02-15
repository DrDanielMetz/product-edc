package org.eclipse.tractusx.ssi.extensions.core.proof.verify;

import lombok.NonNull;
import org.bouncycastle.math.ec.rfc8032.Ed25519;
import org.eclipse.tractusx.ssi.extensions.core.proof.hash.HashedLinkedData;
import org.eclipse.tractusx.ssi.spi.did.DidParser;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidDocument;
import org.eclipse.tractusx.ssi.spi.did.Ed25519VerificationKey2020;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;

import java.net.URI;

public class LinkedDataVerifier {

  public final DidDocumentResolver didDocumentResolver;

  public LinkedDataVerifier(DidDocumentResolver didDocumentResolver) {
    this.didDocumentResolver = didDocumentResolver;
  }

  public boolean verify(HashedLinkedData hashedLinkedData, VerifiableCredential credential) {

    final @NonNull URI issuer = credential.getIssuer();
    final Did issuerDid = DidParser.parse(issuer);
    final DidDocument document = didDocumentResolver.resolve(issuerDid);

    final URI verificationMethodId = credential.getProof().getVerificationMethod();
    final Ed25519VerificationKey2020 key = document.getPublicKeys().stream()
        .filter(v -> v.getId().equals(verificationMethodId))
        .map(Ed25519VerificationKey2020.class::cast)
        .findFirst()
        .orElseThrow();

    final MultibaseString publicKey = key.getMultibaseString();
    final MultibaseString signature = credential.getProof().getProofValueMultiBase();

    final boolean signatureValid = Ed25519.verify(
        signature.getDecoded(),
        0,
        publicKey.getDecoded(),
        0,
        hashedLinkedData.getValue(),
        0,
        hashedLinkedData.getValue().length);

    return signatureValid;
  }
}
