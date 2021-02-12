package me.m92.tatbook_web.userprofile.repository;

import me.m92.tatbook_web.userprofile.Email;
import me.m92.tatbook_web.userprofile.EmailsAndMobilesView;
import me.m92.tatbook_web.userprofile.Mobile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class HelperUserProfileRepository {

    @PersistenceContext
    private EntityManager eM;

    public boolean existsByEmail(Email email) {
        final List<EmailsAndMobilesView> eamv = eM.createNamedQuery("emails.and.mobiles.findByEmail", EmailsAndMobilesView.class)
                                                    .setParameter("email", email)
                                                    .getResultList();
        return !(eamv.isEmpty()) ? true : false;
    }

    public boolean existsByMobile(Mobile mobile) {
        final List<EmailsAndMobilesView> eamv = eM.createNamedQuery("emails.and.mobiles.findByMobile", EmailsAndMobilesView.class)
                                                    .setParameter("mobile", mobile)
                                                    .getResultList();
        return !(eamv.isEmpty()) ? true : false;
    }

    public boolean existsByEmailOrMobile(Email email, Mobile mobile) {
        final List<EmailsAndMobilesView> eamv = eM.createNamedQuery("emails.and.mobiles.findByEmailAndMobile", EmailsAndMobilesView.class)
                                                    .setParameter("email", email)
                                                    .setParameter("mobile", mobile)
                                                    .getResultList();
        return !(eamv.isEmpty()) ? true : false;
    }
}
