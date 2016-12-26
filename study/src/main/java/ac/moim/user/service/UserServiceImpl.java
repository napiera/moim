package ac.moim.user.service;

import ac.moim.user.dto.UserDto;
import ac.moim.user.entity.User;
import ac.moim.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Service;

/**
 * Created by SONG_HOHOON on 2016-12-22.
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;

	public void saveUser(UserDto.Request user) {
	}

	public ac.moim.user.entity.User saveUser(Person person) {
		return userRepository.saveAndFlush(personToUser(person));
	}

	private User personToUser(Person person) {
		User user = new User();

		user.setId(person.getId());
		user.setName(person.getGivenName());
		user.setMail(person.getAccountEmail());
		user.setGender(person.getGender());
		user.setBirthday(person.getBirthday());
		user.setIntro(person.getAboutMe());

		return user;
	}
}
