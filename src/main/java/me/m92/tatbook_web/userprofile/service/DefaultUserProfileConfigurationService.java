package me.m92.tatbook_web.userprofile.service;

import me.m92.tatbook_web.projection.ApplicationResponse;
import me.m92.tatbook_web.projection.HealthConditionProjection;
import me.m92.tatbook_web.projection.TextApplicationResponse;
import me.m92.tatbook_web.projection.userprofile_configuration.ClientProfileConfiguration;
import me.m92.tatbook_web.projection.userprofile_configuration.TattooistProfileConfiguration;
import me.m92.tatbook_web.security.CurrentlyLoggedInUserProfileUtil;
import me.m92.tatbook_web.studio.TattooStudio;
import me.m92.tatbook_web.studio.mapper.TattooStudioMapper;
import me.m92.tatbook_web.userprofile.*;
import me.m92.tatbook_web.userprofile.mapper.ColorPreferenceMapper;
import me.m92.tatbook_web.userprofile.mapper.HealthConditionMapper;
import me.m92.tatbook_web.userprofile.mapper.StylePreferenceMapper;
import me.m92.tatbook_web.userprofile.mapper.TattooistProfileBioMapper;
import me.m92.tatbook_web.userprofile.repository.ClientProfileRepository;
import me.m92.tatbook_web.userprofile.repository.TattooistProfileRepository;
import me.m92.tatbook_web.validator.DomainValidationException;
import me.m92.tatbook_web.validator.TattooistProfileConfigurationDomainValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
class DefaultUserProfileConfigurationService implements UserProfileConfigurationService {

    private TattooistProfileRepository tattooistProfileRepository;

    private ClientProfileRepository clientProfileRepository;

    private TattooistProfileConfigurationDomainValidator tattooistConfigurationValidator;

    private TattooStudioMapper studioMapper;

    private TattooistProfileBioMapper bioMapper;

    private StylePreferenceMapper stylePreferenceMapper;

    private ColorPreferenceMapper colorPreferenceMapper;

    private HealthConditionMapper healthConditionMapper;

    private CurrentlyLoggedInUserProfileUtil currentlyLoggedInUserProfileUtil;

    @Autowired
    public DefaultUserProfileConfigurationService(TattooistProfileRepository tattooistProfileRepository,
                                                  ClientProfileRepository clientProfileRepository,
                                                  TattooistProfileConfigurationDomainValidator tattooistConfigurationValidator,
                                                  TattooStudioMapper studioMapper,
                                                  TattooistProfileBioMapper bioMapper,
                                                  StylePreferenceMapper stylePreferenceMapper,
                                                  ColorPreferenceMapper colorPreferenceMapper,
                                                  HealthConditionMapper healthConditionMapper,
                                                  CurrentlyLoggedInUserProfileUtil currentlyLoggedInUserProfileUtil) {
        this.tattooistProfileRepository = tattooistProfileRepository;
        this.clientProfileRepository = clientProfileRepository;
        this.tattooistConfigurationValidator = tattooistConfigurationValidator;
        this.studioMapper = studioMapper;
        this.bioMapper = bioMapper;
        this.stylePreferenceMapper = stylePreferenceMapper;
        this.colorPreferenceMapper = colorPreferenceMapper;
        this.healthConditionMapper = healthConditionMapper;
        this.currentlyLoggedInUserProfileUtil = currentlyLoggedInUserProfileUtil;
    }

    @Override
    public ApplicationResponse configure(TattooistProfileConfiguration tattooistProfileConfiguration) throws DomainValidationException {
        tattooistConfigurationValidator.validate(tattooistProfileConfiguration, () -> {
            final Optional<TattooistProfile> tattooistProfile = tattooistProfileRepository.findById(currentlyLoggedInUserProfileUtil.getId());
            if(tattooistProfile.isPresent()) {
                final TattooistProfile existingTattooistProfile = tattooistProfile.get();
                final TattooStudio studio = studioMapper.mapFromConfiguration(tattooistProfileConfiguration.getStudioSelectionConfiguration());
                final TattooistProfileBio bio = bioMapper.mapFrom(tattooistProfileConfiguration.getBioConfiguration());
//                final List<Style> styles = tattooistProfileConfiguration.getStylePreferenceConfiguration()
//                                                                        .getStyles()
//                                                                        .stream()
//                                                                        .map(styleProjection -> styleMapper.mapFrom(styleProjection))
//                                                                        .collect(Collectors.toList());
                existingTattooistProfile.changeStudio(studio);
                existingTattooistProfile.changeBio(bio);
                existingTattooistProfile.beginVerification();
                tattooistProfileRepository.update(existingTattooistProfile);
            }
        });
        return TextApplicationResponse.ok("Tattooist profile has been configured and should awaits verification");
    }

    @Override
    public ApplicationResponse configure(ClientProfileConfiguration clientProfileConfiguration) {
        final Optional<ClientProfile> clientProfile = clientProfileRepository.findById(currentlyLoggedInUserProfileUtil.getId());
        if(clientProfile.isPresent()) {
            final ClientProfile existingClientProfile = clientProfile.get();
            final List<StylePreference> styles = clientProfileConfiguration.getStylePreferenceConfiguration()
                                                                    .getStyles()
                                                                    .stream()
                                                                    .map(styleProjection -> stylePreferenceMapper.mapFrom(styleProjection))
                                                                    .collect(Collectors.toList());
            final ColorPreference colorPreferences = colorPreferenceMapper.mapFrom(clientProfileConfiguration.getColorPreferenceConfiguration());
            existingClientProfile.changeStylePreferences(styles);
            existingClientProfile.changeColorPreference(colorPreferences);
            clientProfileRepository.update(existingClientProfile);
        }
        return TextApplicationResponse.ok("Client profile has benn configured and should awaits verification");
    }

    @Override
    public ApplicationResponse receiveHealthCondition(HealthConditionProjection healthConditionProjection) {
        final HealthCondition healthCondition = healthConditionMapper.mapToHealthCondition(healthConditionProjection);
        Optional<ClientProfile> clientProfile = clientProfileRepository.findById(currentlyLoggedInUserProfileUtil.getId());
        clientProfile.ifPresent(profile -> {
            profile.describeHealthCondition(healthCondition);
            clientProfileRepository.save(profile);
        });
        return TextApplicationResponse.ok("Client's health condition has been changed!");
    }
}
