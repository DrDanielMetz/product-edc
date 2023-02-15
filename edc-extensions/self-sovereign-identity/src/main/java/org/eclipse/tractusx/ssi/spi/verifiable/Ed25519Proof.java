package org.eclipse.tractusx.ssi.spi.verifiable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.tractusx.ssi.extensions.core.base.Base58Bitcoin;

import java.net.URI;
import java.util.Date;

@Value
@Builder
@ToString
@EqualsAndHashCode
public class Ed25519Proof {

  public static final String TYPE = "Ed25519Signature2020";

  @NonNull
  @Builder.Default
  String type = "Ed25519Signature2020";

  @NonNull
  @Builder.Default
  String proofPurpose = "assertionMethod";

  @NonNull
  Date created;
  @NonNull
  URI verificationMethod;
  @NonNull
  String proofValue;
  MultibaseString proofValueMultiBase;
  public MultibaseString getProofValueMultiBase(){
    return Base58Bitcoin.create(proofValue);
    //return MultibaseFactory.create(proofValue); // TODO must be cleaner
  }
}
