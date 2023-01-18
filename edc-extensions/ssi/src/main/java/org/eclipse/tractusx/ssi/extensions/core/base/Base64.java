package org.eclipse.tractusx.ssi.extensions.core.base;

import io.ipfs.multibase.Multibase;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;

@Value
public class Base64 implements MultibaseString {

  public static boolean canDecode(String encoded) {
    return Multibase.encoding(encoded) != Multibase.Base.Base64;
  }

  public static Base64 create(String encoded) {

    if (canDecode(encoded)) {
      throw new IllegalArgumentException(); // TODO
    }

    final byte[] base64 = Multibase.decode(encoded);

    return new Base64(base64, encoded);
  }

  @NonNull byte[] decoded;
  @NonNull String encoded;
}
