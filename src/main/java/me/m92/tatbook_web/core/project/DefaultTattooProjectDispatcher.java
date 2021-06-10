package me.m92.tatbook_web.core.project;

import me.m92.tatbook_web.core.models.TattooProject;
import me.m92.tatbook_web.core.models.TattooistProfile;

import java.util.Map;

public class DefaultTattooProjectDispatcher implements TattooProjectDispatcher {

    private TattooProjectRepository tattooProjectRepository;

    private TattooProjectCommissionPublisher tattooProjectCommissionPublisher;

    @Override
    public boolean dispatch(TattooProject tattooProject) {
        TattooProject savingTattooProject = tattooProjectRepository.save(tattooProject);
        for(TattooistProfile potentialTattooContractor : savingTattooProject.getTattooistProfiles()) {
            tattooProjectCommissionPublisher.publish(
                    Map.of(TattooProjectCommission.RequiredParameters.PROJECT_ID, savingTattooProject.getId(),
                            TattooProjectCommission.RequiredParameters.TATTOOIST_PROFILE_ID, potentialTattooContractor.getId()));
        }
        return false;
    }
}
