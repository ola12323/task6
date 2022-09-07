package com.example.task6.serv;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.task6.model.ParamDto;
import com.example.task6.model.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.example.task6.util.Constants.cyrillicCharacters;
import static com.example.task6.util.Constants.latinCharacters;

@Service
@RequiredArgsConstructor
public class UserService {


    public List<UserDto> generate(ParamDto paramDto) {

        Faker faker = new Faker(new Locale(paramDto.getSelectedRegion()),
                new Random(Integer.parseInt(paramDto.getSeedNumber())));
        List<UserDto> fakeUsers = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            fakeUsers.add(new UserDto(
                    String.valueOf(faker.number().numberBetween(10000, 99999)),
                    faker.name().fullName(),
                    faker.address().fullAddress().startsWith("#") ? faker.address().fullAddress().substring(7) :
                            faker.address().fullAddress(),
                    faker.phoneNumber().cellPhone(),
                    i
            ));
        }
        generateErrors(fakeUsers, paramDto);
        return fakeUsers;
    }

    public List<UserDto> generateNextData(ParamDto paramDto) {
        Faker faker = new Faker(new Locale(paramDto.getSelectedRegion()),
                new Random(Long.parseLong(paramDto.getSeedNumber())));
        List<UserDto> fakeUsers = new ArrayList<>();
        for (int i = 1; i < paramDto.getDataLength() + 10; i++) {
            if (i >= paramDto.getDataLength()) {
                fakeUsers.add(new UserDto(
                        String.valueOf(faker.number().numberBetween(10000, 99999)),
                        faker.name().fullName(),
                        faker.address().fullAddress().startsWith("#") ? faker.address().fullAddress().substring(7) :
                                faker.address().fullAddress(),
                        faker.phoneNumber().cellPhone(),
                        i
                ));
            } else {
                new UserDto(
                        String.valueOf(faker.number().numberBetween(10000, 99999)),
                        faker.name().fullName(),
                        faker.address().fullAddress(),
                        faker.phoneNumber().cellPhone(),
                        i);
            }
        }
        generateErrors(fakeUsers, paramDto);
        return fakeUsers;

    }

    public void generateErrors(List<UserDto> fakeUsers, ParamDto paramDto) {
        int seedNumber = Integer.parseInt(paramDto.getSeedNumber());
        double errorNumber = Double.parseDouble(paramDto.getErrorPerRecord());
        Random random = new Random(seedNumber);
        for (int i = 0; i < errorNumber; i++) {
            generateErrorToFakeUsers(fakeUsers, paramDto.getSelectedRegion().equals("ru"), random);
        }
    }

    private void generateErrorToFakeUsers(List<UserDto> fakeUsers, boolean isCyrillic, Random random) {
        for (UserDto fakeUser : fakeUsers) {
            int selectField = random.nextInt(3);
            int method = random.nextInt(3);
            switch (selectField) {
                case 0:
                    String fullName = fakeUser.getFullName();
                    fakeUser.setFullName(
                            method == 0 ?
                                    addChar(fullName, false, isCyrillic, random) : method == 1 ?
                                    changeChar(fullName, random) : deleteChar(fullName, random)
                    );
                    break;
                case 1:
                    String address = fakeUser.getAddress();
                    fakeUser.setAddress(
                            method == 0 ?
                                    addChar(address, false, isCyrillic, random) : method == 1 ?
                                    changeChar(address, random) : deleteChar(address, random)
                    );
                    break;
                case 2:
                    String phoneNumber = fakeUser.getPhoneNumber();
                    fakeUser.setPhoneNumber(
                            method == 0 ?
                                    addChar(phoneNumber, true, isCyrillic, random) : method == 1 ?
                                    changeChar(phoneNumber, random) : deleteChar(phoneNumber, random)
                    );
                    break;
                default:
                    break;
            }
        }
    }

    private String addChar(String data, boolean isNumber, boolean isCyrillic, Random random) {
        int index = random.nextInt(data.length());
        if (isCyrillic) {
            return addCharTo(data, isNumber, index, cyrillicCharacters, random);
        } else {
            return addCharTo(data, isNumber, index, latinCharacters, random);
        }
    }

    private String addCharTo(String data, boolean isNumber, int index, String characters, Random random) {
        if (index == data.length() - 1) return isNumber ? data.concat(String.valueOf(random.nextInt(10))) :
                data.concat(String.valueOf(characters.charAt(random.nextInt(52))));
        return isNumber ?
                data.substring(0, index).concat(String.valueOf(random.nextInt(10)).concat(data.substring(index + 1))) :
                data.substring(0, index).concat(String.valueOf(characters.charAt(random.nextInt(52)))) +
                        data.substring(index + 1);
    }

    private String deleteChar(String data, Random random) {
        int index = random.nextInt(data.length());
        return data.replace(data.charAt(index), ' ');
    }

    private String changeChar(String data, Random random) {
        int changeFromIndex = random.nextInt(data.length());
        int changeToIndex = random.nextInt(data.length());
        char[] chars = data.toCharArray();
        char changeFrom = chars[changeFromIndex];
        chars[changeFromIndex] = chars[changeToIndex];
        chars[changeToIndex] = changeFrom;
        StringBuilder result = new StringBuilder();
        for (char aChar : chars) {
            result.append(aChar);
        }
        return result.toString();
    }


}
