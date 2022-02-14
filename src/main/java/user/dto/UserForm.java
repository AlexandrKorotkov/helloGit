package user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.model.User;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class UserForm {

    private String firstName;
    private String lastName;
    private Integer age;
    private boolean isDeleted;

    public static UserForm from(User user) {
        return UserForm.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .build();
    }

    public static List<UserForm> from(List<User> users) {
        return users.stream().map(UserForm::from).collect(Collectors.toList());
    }
}
