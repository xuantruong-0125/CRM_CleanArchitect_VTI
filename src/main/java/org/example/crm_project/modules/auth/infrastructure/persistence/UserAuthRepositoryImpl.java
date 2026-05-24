package org.example.crm_project.modules.auth.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.auth.domain.entity.AuthUser;
import org.example.crm_project.modules.auth.domain.repository.UserAuthRepository;
import org.example.crm_project.modules.auth.infrastructure.mapper.AuthUserMapper;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.repository.UserAuthQueryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAuthRepositoryImpl implements UserAuthRepository {

    private final UserAuthQueryRepository queryRepository;

    @Override
    public Optional<AuthUser> findByUsername(String username) {

        var rows = queryRepository.findUserAuthData(username);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(AuthUserMapper.toAuthUser(rows));
    }
}