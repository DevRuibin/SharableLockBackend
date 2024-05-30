package org.example.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public UserModel createUser(UserRequest userRequest){
        UserModel userModel = UserModel.builder()
                .username(Utils.randomUsername())
                .email(userRequest.getEmail())
                .phone(Utils.randomPhone())
                .avatar(Utils.randomAvatar())
                .password(userRequest.getPassword())
                .gender(Gender.MALE)
                .admin(false)
                .build();
        return userRepository.save(userModel);
    }

    public UserModel findById(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    public UserModel updateUser(Long id, UserModel user) {
        UserModel userModel = userRepository.findById(id).orElseThrow();
        if(user.getEmail() != null) userModel.setEmail(user.getEmail());
        if(user.getUsername() != null) userModel.setUsername(user.getUsername());
        if(user.getPhone() != null) userModel.setPhone(user.getPhone());
        if(user.getAvatar() != null) userModel.setAvatar(user.getAvatar());
        if(user.getPassword() != null) userModel.setPassword(user.getPassword());
        if(user.getGender() != null) userModel.setGender(user.getGender());
        userModel.setAdmin(user.isAdmin());
        return userRepository.save(userModel);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}


class Utils{
    public static String randomAvatar(){
        return "https://avatar.iran.liara.run/public";
    }

    public static String randomUsername(){
        return "user" + (int)(Math.random() * 1000);
    }

    public static String randomPhone(){
        return "09" + (int)(Math.random() * 100000000);
    }

}