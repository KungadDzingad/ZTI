package pl.karinawojtek.ztiserver.utils;

import org.springframework.stereotype.Service;

@Service
public class StringUtil {

    public boolean checkCorrectEmail(String email) {
        if(!email.contains("@")) return false;

        String[] splitMail = email.split("@");
        if(splitMail[0].length() <= 1 || splitMail[1].length() <= 1)
            return false;
        if(!splitMail[1].contains(".") ||
                splitMail[1].charAt(0) == '.' ||
                splitMail[1].charAt(splitMail[1].length()-1) == '.')
            return false;
        return true;
    }

    public boolean checkPhoneNumberString(String phone){
        if(phone.length() != 9) return false;
        for (char c : phone.toCharArray()) {
            if(!Character.isDigit(c)) return false;
        }
        return true;
    }

}
