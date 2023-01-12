package org.eclipse.tractusx.ssi.extensions.core.wallet;

import org.eclipse.tractusx.ssi.extensions.core.exception.SsiWalletAlreadyExistsException;
import org.eclipse.tractusx.ssi.extensions.core.exception.SsiWalletNotFoundException;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWallet;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWalletRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VerifiableCredentialWalletRegistryImpl implements VerifiableCredentialWalletRegistry {

    private final Map<String, VerifiableCredentialWallet> wallets = new HashMap<>();

    @Override
    public VerifiableCredentialWallet get(String walletIdentifier) {

        if (!wallets.containsKey(walletIdentifier))
            throw new SsiWalletNotFoundException(walletIdentifier, new ArrayList<>(wallets.keySet()));

        return wallets.get(walletIdentifier);
    }

    @Override
    public void register(VerifiableCredentialWallet wallet) {
        if (wallets.containsKey(wallet.getIdentifier())) {
            throw new SsiWalletAlreadyExistsException(wallet.getIdentifier());
        }

        wallets.put(wallet.getIdentifier(), wallet);
    }

    @Override
    public void unregister(VerifiableCredentialWallet wallet) {
        if (!wallets.containsKey(wallet.getIdentifier()))
            throw new SsiWalletNotFoundException(wallet.getIdentifier(), new ArrayList<>(wallets.keySet()));

        wallets.remove(wallet.getIdentifier());
    }
}
