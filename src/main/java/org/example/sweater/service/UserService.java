package org.example.sweater.service;

import org.example.sweater.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 UserDetailsService - основной интерфейс, загружающий пользовательские данные.
 Он используется во всем фреймворке как пользовательский DAO и является стратегией, используемой платформой DaoAuthenticationProvider.
 Для интерфейса требуется только один метод, доступный только для чтения, что упрощает поддержку новых стратегий доступа к данным.
 */

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // находит пользователя по имени пользователя
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
