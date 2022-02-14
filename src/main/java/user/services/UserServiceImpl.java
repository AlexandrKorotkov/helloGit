package user.services;

import user.dto.UserForm;
import user.model.User;
import user.repository.UsersRepository;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void createUser(UserForm form) {
        User user = User.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .age(form.getAge())
                .isDeleted(false)
                .build();
        usersRepository.save(user);
    }

    public Boolean isDeleteUser(Long userId) {
        return usersRepository.findById(userId) == null;
    }

    @Override
    public void deleteById(Long userId) {
        usersRepository.deleteById(userId);
    }

    @Override
    public User findById(Long userId) {
        if (usersRepository.findById(userId)!=null) {
            return usersRepository.findById(userId);
        } else {
            throw new IllegalArgumentException("user not found");
        }
    }

    @Override
    public List<User> getAll() {
        return usersRepository.findAll();
    }

    @Override
    public void updateUser(User user) {
        usersRepository.updateUser(user);
    }
}


