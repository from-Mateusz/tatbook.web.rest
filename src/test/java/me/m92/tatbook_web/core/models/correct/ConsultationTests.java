package me.m92.tatbook_web.core.models.correct;

import me.m92.tatbook_web.core.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ConsultationTests {

    @Test
    public void shouldYieldSortedConsultationMessages() {
        TattooistProfile consultationLeader = createRandomTattooistProfile();
        ClientProfile consultationParticipant = createRandomClientProfile();
        LeaderConsultationMessage leaderConsultationMessage1 = LeaderConsultationMessage.create("leaderMessage1",
                LocalDateTime.of(2021, 6, 10, 19, 31), consultationLeader);
        LeaderConsultationMessage leaderConsultationMessage2 = LeaderConsultationMessage.create("leaderMessage2",
                LocalDateTime.of(2021, 6, 10, 19, 33), consultationLeader);
        ParticipantConsultationMessage participantConsultationMessage = ParticipantConsultationMessage.create("participantMessage",
                LocalDateTime.of(2021, 6, 10, 19, 32), consultationParticipant);

        Consultation consultation = createRandomConsultation();
        consultation.addLeaderMessage(leaderConsultationMessage1);
        consultation.addLeaderMessage(leaderConsultationMessage2);
        consultation.addParticipantMessage(participantConsultationMessage);

        List<ConsultationMessage> consultationMessages = consultation.getConsultationMessages();
        ConsultationMessage exemplaryParticipantMessage = consultationMessages.get(1);
        assertThat(consultationMessages, hasSize(3));
        assertThat(exemplaryParticipantMessage.getContent(), equalTo("participantMessage"));
    }

    private Consultation createRandomConsultation() {
        return Consultation.create(
                ConsultationDueDate.create(LocalDateTime.of(2021, 6, 10, 19, 30),
                        TattooProject.createEmpty()));
    }

    private ClientProfile createRandomClientProfile() {
        return ClientProfile.create("Kasia", "kasia@gmail.com", "590200300");
    }

    private TattooistProfile createRandomTattooistProfile() {
        return TattooistProfile.create("Adam", "adam@studio.com", "698210390",
                "adamTattoos");
    }
}
