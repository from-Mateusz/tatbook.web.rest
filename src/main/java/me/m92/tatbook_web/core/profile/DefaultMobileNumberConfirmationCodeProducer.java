package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.security.utils.Moment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
class DefaultMobileNumberConfirmationCodeProducer implements MobileNumberConfirmationCodeProducer {

    private static final Random RANDOM = new Random();

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private static final List<List<Character>> DICTIONARIES = new ArrayList<>();

    static {
        List<Character> lowercase = IntStream.range(97, 123)
                .mapToObj(value -> (char)value)
                .collect(Collectors.toList());
        List<Character> uppercase = IntStream.range(65, 91)
                .mapToObj(value -> (char)value)
                .collect(Collectors.toList());
        List<Character> units = IntStream.range(48, 58)
                .mapToObj(value -> (char)value)
                .collect(Collectors.toList());
        DICTIONARIES.addAll(List.of(lowercase, uppercase, units));
    }


    @Override
    public MobileConfirmationCode produce(String mobileNumber) {
        StringBuilder code = new StringBuilder();
        while(code.length() < 9) {
            if((code.length() > 0 && code.length() < 8) && code.length() % 4 == 0) {
                code.append("-");
            }
            List<Character> randomDictionary = DICTIONARIES.get(RANDOM.nextInt(DICTIONARIES.size()));
            code.append(randomDictionary.get(RANDOM.nextInt(randomDictionary.size())));
        }


        Moment moment = Moment.getInstance();
        StringJoiner join = new StringJoiner(".");
        String sweetenedCode = join.add(code.toString())
                .add(String.valueOf(moment.getTime()))
                .add(mobileNumber)
                .toString();


        String encodedCode = hash(sweetenedCode);
        StringBuilder hardenedCode = new StringBuilder();
        for(int i = 0; hardenedCode.length() < 9 && i < encodedCode.length(); i++) {
            int ch = (int) encodedCode.charAt(i);
            if((hardenedCode.length() > 0 && hardenedCode.length() < 8) && hardenedCode.length() % 4 == 0) {
                hardenedCode.append("-");
            }
            if(((48 <= ch) && (ch >= 57)) || ((65 <= ch) && (ch >= 91)) || ((97 <= ch) && (ch >= 123))) {
                hardenedCode.append(encodedCode.charAt(i));
            }
        }
        return MobileConfirmationCode.create(hardenedCode.toString(), hash(hardenedCode.toString()));
    }

    @Override
    public String hash(String code) {
        return ENCODER.encode(code);
    }

    @Override
    public boolean compare(String original, String hash) {
        return ENCODER.matches(original, hash);
    }
}
