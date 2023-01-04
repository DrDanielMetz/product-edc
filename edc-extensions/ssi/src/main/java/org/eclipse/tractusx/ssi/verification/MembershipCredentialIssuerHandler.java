package org.eclipse.tractusx.ssi.verification;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import org.eclipse.tractusx.ssi.credentials.CredentialType;
import org.eclipse.tractusx.ssi.setting.SsiSettings;

public class MembershipCredentialIssuerHandler implements VerifiableCredentialVerificationHandler {

    private final SsiSettings ssiSettings;

    public MembershipCredentialIssuerHandler(SsiSettings ssiSettings) {
        this.ssiSettings = ssiSettings;
    }

    @Override
    public boolean canHandle(VerifiableCredential credential) {
        return credential.getCredentialSubject().isType(CredentialType.MEMBERSHIP_CREDENTIAL);
    }

    @Override
    public boolean checkTrust(VerifiableCredential credential) {
        return credential.getIssuer().equals(ssiSettings.getDidDataspaceOperator().toUri());
    }
}
