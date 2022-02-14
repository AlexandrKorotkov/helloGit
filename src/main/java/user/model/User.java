package user.model;

import lombok.*;
import user.dto.UserForm;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Long id;

    private String firstName;
    private String lastName;
    private Integer age;
    private boolean isDeleted;

    public static User from(UserForm userForm) {
        return User.builder()
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .age(userForm.getAge())
                .build();
    }

    public static List<User> from(List<UserForm> userForm) {
        return userForm.stream().map(User::from).collect(Collectors.toList());
    }
}

