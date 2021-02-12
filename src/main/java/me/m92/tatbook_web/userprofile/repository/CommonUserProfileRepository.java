package me.m92.tatbook_web.userprofile.repository;

import me.m92.tatbook_web.userprofile.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CommonUserProfileRepository {

    private TattooistProfileRepository tattooistProfileRepository;

    private ClientProfileRepository clientProfileRepository;

    private OnlyTattooist onlyTattooist;

    private OnlyClient onlyClient;

    @Autowired
    public CommonUserProfileRepository(final TattooistProfileRepository tattooistProfileRepository, final ClientProfileRepository clientProfileRepository) {
        this.tattooistProfileRepository = tattooistProfileRepository;
        this.clientProfileRepository = clientProfileRepository;
        this.onlyTattooist = OnlyTattooist.getInstance(tattooistProfileRepository);
        this.onlyClient = OnlyClient.getInstance(clientProfileRepository);
    }

    public static class OnlyTattooist {
        static private OnlyTattooist instance;

        private TattooistProfileRepository repository;

        OnlyTattooist(final TattooistProfileRepository repository) {
            this.repository = repository;
            instance = this;
        }

        public static OnlyTattooist getInstance(TattooistProfileRepository repository) {
            if(null == instance) {
                instance = new OnlyTattooist(repository);
            }
            return instance;
        }

        public Optional<TattooistProfile> findByEmail(Email email) {
            return repository.findByEmail(email);
        }
    }

    public static class OnlyClient {
        static private OnlyClient instance;

        private ClientProfileRepository repository;

        OnlyClient(final ClientProfileRepository repository) {
            this.repository = repository;
        }

        public static OnlyClient getInstance(ClientProfileRepository repository) {
            if(null == instance) {
                instance = new OnlyClient(repository);
            }
            return instance;
        }

        public Optional<ClientProfile> findByEmail(Email email) {
            return repository.findByEmail(email);
        }
    }

    public OnlyTattooist tattooist() {
        return this.onlyTattooist;
    }

    public OnlyClient client() {
        return this.onlyClient;
    }
}
