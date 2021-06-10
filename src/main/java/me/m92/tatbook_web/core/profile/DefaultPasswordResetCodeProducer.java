package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.models.PersonalProfile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.StringJoiner;

public class DefaultPasswordResetCodeProducer implements PasswordResetCodeProducer {

    private final static BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Override
    public PasswordResetCode produce(PersonalProfile personalProfile) {
        String emailAddress = personalProfile.getEmailAddress();
        String mobileNumber = personalProfile.getMobileNumber();
        LocalDateTime currentTime = LocalDateTime.now();

        char[] emailAddressChars = emailAddress.toCharArray();
        char[] mobileNumberChars = mobileNumber.toCharArray();

        Arrays.sort(emailAddressChars);
        Arrays.sort(mobileNumberChars);

        StringJoiner joinedCode = new StringJoiner("");
        joinedCode.add(ENCODER.encode(String.valueOf(emailAddressChars)));
        joinedCode.add(currentTime.toString());
        joinedCode.add(ENCODER.encode(String.valueOf(mobileNumberChars)));

        String hardenedCode = ENCODER.encode(joinedCode.toString());
        String moreHardenedCode = ENCODER.encode(hardenedCode);
        String urlEncodedCode = URLEncoder.encode(hardenedCode, StandardCharsets.UTF_8);

        return PasswordResetCode.create(urlEncodedCode, moreHardenedCode);
    }
}
